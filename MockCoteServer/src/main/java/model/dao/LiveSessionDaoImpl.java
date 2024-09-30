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


}
