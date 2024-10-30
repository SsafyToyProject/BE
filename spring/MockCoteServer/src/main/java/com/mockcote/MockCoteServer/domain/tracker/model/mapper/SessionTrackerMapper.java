package com.mockcote.MockCoteServer.domain.tracker.model.mapper;

import org.apache.ibatis.annotations.Param;

import com.mockcote.MockCoteServer.domain.session.dto.Session;
import com.mockcote.MockCoteServer.domain.tracker.dto.SessionTracker;

public interface SessionTrackerMapper {
	/**
	 * Session에 설정된 내용을 기반으로 Session Tracker 삽입
	 * @param session
	 * @return affected row
	 */
	int insertSessionTrackers(Session session);
	
	SessionTracker findSessionTracker(@Param("sessionId") int sessionId, @Param("userId") int userId, @Param("problemId") int problemId);
	
	/**
	 * SessionTracker 정보 업데이트
	 * @param tracker
	 * @return affected row
	 */
	int updateSessionTracker(SessionTracker sessionTracker);
}
