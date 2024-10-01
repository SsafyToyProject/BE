package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.dao.SessionTrackerDao;
import model.dto.SessionTrackerDto;
import util.DBUtil;

public class SessionTrackerDaoImpl implements SessionTrackerDao {

	@Override
	public SessionTrackerDto getSessionTrackerById(int sessionId, int userId, int problemId) {
	    String sql = "SELECT session_id, user_id, problem_id, solved_at, performance, language, code_link, description " +
	                 "FROM session_tracker WHERE session_id = ? AND user_id = ? AND problem_id = ?";
	    SessionTrackerDto tracker = null;
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = DBUtil.getConnection();
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, sessionId);
	        ps.setInt(2, userId);
	        ps.setInt(3, problemId);

	        rs = ps.executeQuery();
	        if (rs.next()) {
	            tracker = new SessionTrackerDto(
	                rs.getInt("session_id"),
	                rs.getInt("user_id"),
	                rs.getInt("problem_id"),
	                rs.getTimestamp("solved_at"),
	                rs.getInt("performance"),
	                rs.getString("language"),
	                rs.getString("code_link"),
	                rs.getString("description")
	            );
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.close(rs, ps, conn);
	    }

	    return tracker;
	}


	@Override
    public boolean insertSessionTracker(int sessionId, int userId, int problemId) {
        String sql = "INSERT INTO session_tracker (session_id, user_id, problem_id, solved_at, performance, language, code_link, description) " +
                     "VALUES (?, ?, ?, NULL, NULL, NULL, NULL, NULL)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, sessionId);
            ps.setInt(2, userId);
            ps.setInt(3, problemId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }

        return false;
    }


	@Override
    public boolean deleteSessionTracker(int sessionId, int userId) {
        String sql = "DELETE FROM session_tracker WHERE session_id = ? AND user_id = ?";

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
    
    
	public void updateSessionTracker(SessionTrackerDto dto){
		//존재하지 않으면 update하고, 존재할경우 performance가 더 낮을 때 update
		
		//업데이트가 필요한지 확인하기 위해 기존 dto 받기
		SessionTrackerDto origin = getSessionTrackerById(dto.getSession_id(), dto.getUser_id(), dto.getProblem_id());
		
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
}
