package service;

import model.dto.SessionDto;

public interface SessionService {
	/**
	 * 새로운 session, session_problems, session_participants을 추가하고,
	 * 모든 구성원과 문제들에 대해 tracker를 추가합니다.
	 * @param dto 세션 생성 관련 정보 (session_id 제외)
	 */
	void addSession(SessionDto dto);
}
