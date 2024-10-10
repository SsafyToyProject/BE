package crawler;

//deprecated

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.dto.SessionDto;
import model.dto.SessionTrackerDto;
import util.DBUtil;

/**
 * 크롤링을 위해 필요한 DB 메서드들
 */
public class CrawlerDao {
	
	/**
	 * 크롤러를 위한 sessiontracker 업데이트 메서드.
	 * 반복적으로 크롤링된 레코드들에 대해 업데이트가 필요한 트래커만 업데이트.
	 * @param dto 크롤링된 세션트래커 객체
	 */
	public static void updateTracker(SessionTrackerDto dto) {
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
	
	/**
	 * Session Tracker Dto 하나를 반환합니다.
	 * @param session_id
	 * @param user_id
	 * @param problem_id
	 * @return 해당하는 tracker dto
	 */
	private static SessionTrackerDto getTrackerDto(int session_id, int user_id, int problem_id) {
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
