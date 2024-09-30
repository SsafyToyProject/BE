package model.dao;
//deprecated
import java.util.List;

import model.dto.SessionDto;
import model.dto.SessionTrackerDto;

/**
 * Live session crawling에 필요한 DAO
 */
public interface LiveSessionDao {
	
	/**
	 * 새로운 session, session_problems, session_participants을 추가하고,
	 * 모든 구성원과 문제들에 대해 tracker를 추가합니다.
	 * @param dto 세션 생성 관련 정보 (session_id 제외)
	 */
	void addSession(SessionDto dto);
	
}
