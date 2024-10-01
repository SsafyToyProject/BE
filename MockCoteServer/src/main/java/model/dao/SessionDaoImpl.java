package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.SessionDao;
import model.dto.SessionDto;
import util.DBUtil;

public class SessionDaoImpl implements SessionDao {

	// 모든 세션 가져오기
    @Override
    public List<SessionDto> getAllSessions() {
        List<SessionDto> sessions = new ArrayList<>();
        String sql = "SELECT session_id, study_id, start_at, end_at FROM session";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                sessions.add(new SessionDto(
                    rs.getInt("session_id"),
                    rs.getInt("study_id"),
                    rs.getTimestamp("start_at"),
                    rs.getTimestamp("end_at")
                ));
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
        String sql = "SELECT session_id, study_id, start_at, end_at FROM session WHERE session_id = ?";
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
                session = new SessionDto(
                    rs.getInt("session_id"),
                    rs.getInt("study_id"),
                    rs.getTimestamp("start_at"),
                    rs.getTimestamp("end_at")
                );
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
    public boolean insertSession(SessionDto session)  {
        String sql = "INSERT INTO session (session_id, study_id, start_at, end_at) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, session.getSession_id());
            ps.setInt(2, session.getStudy_id());
            ps.setTimestamp(3, session.getStart_at());
            ps.setTimestamp(4, session.getEnd_at());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }

        return false;
    }


    // 세션 삭제
    @Override
    public boolean deleteSession(int sessionId)  {
        String sql = "DELETE FROM session WHERE session_id = ?";
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
                ret.add(new SessionDto(
                    rs.getInt("session_id"),
                    rs.getInt("study_id"),
                    rs.getTimestamp("start_at"),
                    rs.getTimestamp("end_at")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }

        return ret;
    }
	
	//참가자 추가
    @Override
    public boolean insertParticipant(int sessionId, int userId)  {
        String sql = "INSERT INTO session_participants (session_id, user_id) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, sessionId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }

        return false;
    }

	//문제 추가
    @Override
    public boolean insertProblem(int sessionId, int problemId)  {
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

}
