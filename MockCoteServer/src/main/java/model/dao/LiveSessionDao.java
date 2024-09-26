package model.dao;

import java.util.List;

import model.dto.SessionDto;
import model.dto.SessionTrackerDto;

/**
 * Live session crawling에 필요한 DAO
 */
public interface LiveSessionDao {
	/**
	 * 현재 진행중인 session들을 반환합니다.
	 * @return 활성 상태의 sessionDto list
	 */
	List<SessionDto> getActiveSessions();
	
	/**
	 * 세션의 tracker info를 모두 반환합니다.
	 * @param session_id 세션 ID
	 * @return 해당 세션에 속한 트래커 Dto list
	 */
	List<SessionTrackerDto> getTrackersBySessionId(int session_id);
	
	/**
	 * 새로운 세션을 추가하고, 모든 구성원과 문제들에 대해 tracker를 추가합니다.
	 * @param dto 세션 생성 관련 정보 (session_id 제외)
	 */
	void addSession(SessionDto dto);
	
	/**
	 * tracker 정보를 업데이트합니다.
	 * 이미 정보가 존재할 경우 실행 시간이 단축되는 경우에만 업데이트 합니다.
	 * @param dto 업데이트할 새로운 dto
	 */
	void updateTracker(SessionTrackerDto dto);
}
