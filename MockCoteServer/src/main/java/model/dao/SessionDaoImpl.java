package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.SessionDao;
import model.dto.SessionDto;
import model.dto.UserDto;
import util.DBUtil;

public class SessionDaoImpl implements SessionDao {
	private static SessionDao instance = new SessionDaoImpl();

	private SessionDaoImpl() {
	}

	public static SessionDao getInstance() {
		return instance;
	}

	// 모든 세션 가져오기
	@Override
	public List<SessionDto> getAllSessions() {
		List<SessionDto> sessions = new ArrayList<>();
		String sql = "SELECT * FROM sessions";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				sessions.add(new SessionDto(rs.getInt("session_id"), rs.getInt("study_id"), rs.getInt("query_id"),
						rs.getTimestamp("start_at"), rs.getTimestamp("end_at"), rs.getString("problem_pool")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, ps, conn);
		}

		return sessions;
	}

	// 특정 ID의 세션 가져오기
	@Override
	public SessionDto getSessionById(int sessionId) {
		String sql = "SELECT * FROM sessions WHERE session_id = ?";
		SessionDto session = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sessionId);

			rs = ps.executeQuery();
			if (rs.next()) {
				session = new SessionDto(rs.getInt("session_id"), rs.getInt("study_id"), rs.getInt("query_id"),
						rs.getTimestamp("start_at"), rs.getTimestamp("end_at"), rs.getString("problem_pool"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, ps, conn);
		}

		return session;
	}

	// 세션 삽입
	@Override
	public int insertSession(SessionDto session) {
		// TODO : session dto 수정에 따른 insert 수정 필요
		String sql = "INSERT INTO sessions (session_id, study_id, query_id, start_at, end_at, problem_pool)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int ret = -1;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setInt(1, session.getSession_id());
			ps.setInt(2, session.getStudy_id());
			ps.setInt(3, session.getQuery_id());
			ps.setTimestamp(3, session.getStart_at());
			ps.setTimestamp(4, session.getEnd_at());
			ps.setString(5, session.getProblem_pool());

			if (ps.executeUpdate() > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next())
					ret = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, ps, conn);
		}

		return ret;
	}

	// 세션 삭제
	@Override
	public boolean deleteSession(int sessionId) {
		String sql = "DELETE FROM sessions WHERE session_id = ?";
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, sessionId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, conn);
		}

		return false;
	}

	// 진행중인 세션의 정보들을 반환
	@Override
	public List<SessionDto> getActiveSessions() {
		List<SessionDto> ret = new ArrayList<>();
		String sql = "SELECT * FROM sessions WHERE NOW() BETWEEN start_at AND end_at";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ret.add(new SessionDto(rs.getInt("session_id"), rs.getInt("study_id"), rs.getInt("query_id"),
						rs.getTimestamp("start_at"), rs.getTimestamp("end_at"), rs.getString("problem_pool")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}

		return ret;
	}

	// 참가자 추가
	@Override
	public boolean insertParticipant(int sessionId, int user_id) {
		String sql = "INSERT INTO session_participants (session_id, user_id) VALUES (?, ?)";
		Connection conn = null;
		PreparedStatement ps = null;
		boolean ret = true;
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sessionId);
			ps.setInt(2, user_id);
			if (ps.executeUpdate() == 0) {
				ret = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, conn);
		}

		return ret;
	}

	// 문제 추가
	@Override
	public boolean insertProblem(int sessionId, int problemId) {
		String sql = "INSERT INTO session_problems (session_id, problem_id) VALUES (?, ?)";
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, sessionId);
			ps.setInt(2, problemId);

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, conn);
		}

		return false;
	}
	
	//세션 참여자 추가
	@Override
	public int addParticipant(int session_id, int user_id) {
		String sql = "INSERT INTO session_participants (session_id, user_id) VALUES (?, ?)";
		Connection conn = null;
		PreparedStatement ps = null;
		
		int cnt = -1;
		
		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, session_id);
			ps.setInt(2, user_id);
			
			cnt = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, conn);
		}
		
		return cnt; //성공시 1, 실패시 -1 반환
	}
	
	// 특정 studyId로 세션 목록 가져오기
    @Override
    public List<SessionDto> getSessionsByStudyId(int studyId) {
        List<SessionDto> sessions = new ArrayList<>();
        String sql = "SELECT * FROM sessions WHERE study_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studyId);
            rs = ps.executeQuery();

            while (rs.next()) {
                sessions.add(new SessionDto(rs.getInt("session_id"), rs.getInt("study_id"), rs.getInt("query_id"),
                        rs.getTimestamp("start_at"), rs.getTimestamp("end_at"), rs.getString("problem_pool")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }

        return sessions;
    }
    
 // 세션의 참가자 목록 가져오기
    @Override
    public List<Integer> getParticipantsBySessionId(int sessionId) {
        List<Integer> participants = new ArrayList<>();
        String sql = "SELECT user_id FROM session_participants WHERE session_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sessionId);
            rs = ps.executeQuery();

            while (rs.next()) {
                participants.add(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }

        return participants;
    }

    // 세션의 문제 목록 가져오기
    @Override
    public List<Integer> getProblemsBySessionId(int sessionId) {
        List<Integer> problems = new ArrayList<>();
        String sql = "SELECT problem_id FROM session_problems WHERE session_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sessionId);
            rs = ps.executeQuery();

            while (rs.next()) {
                problems.add(rs.getInt("problem_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }

        return problems;
    }
	

	@Override
	public List<SessionDto> getReadySessions() {
		List<SessionDto> ret = new ArrayList<>();
		String sql = "SELECT * FROM sessions WHERE NOW() BETWEEN DATE_SUB(start_at, INTERVAL 5 MINUTE) AND start_at";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql2 = "select count(problem_id) from session_problems where session_id=?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//시작시간이 5분 이하로 남은 세션들 찾기
			List<SessionDto> sessions = new ArrayList<>();
			while (rs.next()) {
				sessions.add(new SessionDto(rs.getInt("session_id"), rs.getInt("study_id"), rs.getInt("query_id"),
						rs.getTimestamp("start_at"), rs.getTimestamp("end_at"), rs.getString("problem_pool")));
			}
			DBUtil.close(rs,pstmt);
			
			pstmt = conn.prepareStatement(sql2);
			for (SessionDto session : sessions) {
				// problem이 설정되지 않은 session 찾기
				pstmt.setInt(1, session.getSession_id());
				rs = pstmt.executeQuery();
				if(rs.next() && rs.getInt(1) == 0) {
					ret.add(session);
				}
				DBUtil.close(rs,pstmt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return ret;
	}

	@Override
	public List<Integer> getParticipantsById(int session_id) {
		String sql = "SELECT * FROM session_participants where session_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> ret = new ArrayList<>();
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, session_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ret.add(rs.getInt("user_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return ret;
	}

}
