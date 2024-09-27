package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.dto.ProblemDto;
import model.dto.SessionDto;
import model.dto.SessionTrackerDto;
import model.dto.UserDto;
import util.DBUtil;

public class LiveSessionDaoImpl implements LiveSessionDao {

	// singleton
	private static LiveSessionDao instance = new LiveSessionDaoImpl();

	private LiveSessionDaoImpl() {
	}

	public static LiveSessionDao getInstance() {
		return instance;
	}

	@Override
	public List<SessionDto> getActiveSessions() {
		List<SessionDto> ret = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from sessions where now() between start_at and end_at";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(new SessionDto(rs.getInt("session_id"), rs.getInt("study_id"), rs.getTimestamp("start_at"),
						rs.getTimestamp("end_at")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return ret;
	}

	@Override
	public List<SessionTrackerDto> getTrackersBySessionId(int session_id) {
		List<SessionTrackerDto> ret = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from session_trackers where session_id=?";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, session_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(new SessionTrackerDto(session_id, rs.getInt("study_id"), rs.getInt("problem_id"),
						rs.getTimestamp("solved_at"), rs.getInt("performance"), rs.getString("language"),
						rs.getString("code_link"), rs.getString("description")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return ret;
	}

	@Override
	public void addSession(SessionDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			// session table에 추가
			String sql = "insert into sessions(study_id,start_at,end_at) values(?,?,?)";
			pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, dto.getStudy_id());
			pstmt.setTimestamp(2, dto.getStart_at());
			pstmt.setTimestamp(3, dto.getEnd_at());
			rs = pstmt.getGeneratedKeys();
			int session_id = -1;
			if (rs.next())
				session_id = rs.getInt(1);
			else
				throw new Exception("session insert failed");
			DBUtil.close(rs, pstmt);

			// session_participants에 추가
			sql = "insert into session_participants(session_id,user_id) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			for (UserDto user : dto.getSessionParticipant()) {
				pstmt.setInt(1, session_id);
				pstmt.setInt(2, user.getUser_id());
				pstmt.executeUpdate();
			}
			DBUtil.close(pstmt);

			// session_problems에 추가
			sql = "insert into session_problems(session_id,problem_id) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			for (ProblemDto problem : dto.getSessionProblem()) {
				pstmt.setInt(1, session_id);
				pstmt.setInt(2, problem.getProblem_id());
				pstmt.executeUpdate();
			}
			DBUtil.close(pstmt);

			// session_trackers에 추가
			sql = "insert into session_trackers(session_id,user_id,problem_id) values(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			for (UserDto user : dto.getSessionParticipant()) {
				for (ProblemDto problem : dto.getSessionProblem()) {
					pstmt.setInt(1, session_id);
					pstmt.setInt(2, user.getUser_id());
					pstmt.setInt(3, problem.getProblem_id());
					pstmt.executeUpdate();
				}
			}
			DBUtil.close(pstmt);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
	}

	@Override
	public void updateTracker(SessionTrackerDto dto) {
		//존재하지 않으면 update하고, 존재할경우 performance가 더 낮을 때 update
		
		//업데이트가 필요한지 확인하기 위해 기존 dto 받기
		SessionTrackerDto origin = getTrackerDto(dto.getSession_id(), dto.getUser_id(), dto.getProblem_id());
		
		if(origin.getSolved_at() != null && origin.getPerformance() < dto.getPerformance()) {
			//업데이트가 필요하지 않음
			return;
		}
		
		//업데이트
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update session_trackers set solved_at=?,performace=?,language=?,code_link=?,description=? "
				+ "where session_id=?,user_id=?,problem_id=?";
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			if(origin.getPerformance() == dto.getPerformance()) pstmt.setTimestamp(1, origin.getSolved_at());
			else pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(2, dto.getPerformance());
			pstmt.setString(3, dto.getLanguage());
			pstmt.setString(4, dto.getCode_link());
			pstmt.setString(5, dto.getDescription());
			pstmt.setInt(6, dto.getSession_id());
			pstmt.setInt(7, dto.getUser_id());
			pstmt.setInt(8, dto.getProblem_id());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt, conn);
		}
	}

	@Override
	public SessionTrackerDto getTrackerDto(int session_id, int user_id, int problem_id) {
		SessionTrackerDto ret = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from session_trackers where session_id=? and user_id=? and problem_id=?";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, session_id);
			pstmt.setInt(2, user_id);
			pstmt.setInt(3, problem_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ret = new SessionTrackerDto(session_id, rs.getInt("study_id"), rs.getInt("problem_id"),
						rs.getTimestamp("solved_at"), rs.getInt("performance"), rs.getString("language"),
						rs.getString("code_link"), rs.getString("description"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return ret;
	}

}
