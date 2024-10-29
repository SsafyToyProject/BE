package com.mockcote.MockCoteServer.model.mapper;

import java.util.List;

import com.mockcote.MockCoteServer.dto.Session;

public interface SessionMapper {
	/**
	 * 시작 시간이 3분 이하로 남은 세션 조회
	 * @return session list
	 */
	List<Session> searchReadySessions();
	
	/**
	 * session에 할당된 문제의 갯수 조회
	 * @param session_id
	 * @return 할당된 문제의 갯수
	 */
	int getProblemCount(int session_id);
	
	/**
	 * session 참가자 조회
	 * @param session_id
	 * @return user_id list
	 */
	List<Integer> getParticipants(int session_id);
}
