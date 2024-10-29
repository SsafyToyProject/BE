package com.mockcote.MockCoteServer.model.mapper;

import com.mockcote.MockCoteServer.dto.Session;

public interface SessionTrackerMapper {
	/**
	 * Session에 설정된 내용을 기반으로 Session Tracker 삽입
	 * @param session
	 * @return affected row
	 */
	int insertSessionTrackers(Session session);
}
