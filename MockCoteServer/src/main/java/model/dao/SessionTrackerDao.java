package model.dao;

import java.sql.SQLException;
import java.util.List;
import model.dto.SessionTrackerDto;

public interface SessionTrackerDao {

    /**
     * 특정 세션 ID, 유저 ID, 문제 ID에 해당하는 세션 트래커 정보를 가져오는 메서드
     * @param sessionId 세션 고유 ID
     * @param userId 유저 고유 ID
     * @param problemId 문제 고유 ID
     * @return 세션 트래커 정보를 담은 SessionTrackerDto 객체
     */
    SessionTrackerDto getSessionTrackerById(int sessionId, int userId, int problemId);

    
    /**
     * 새로운 세션 트래커 데이터를 insert
     * 
     * @param sessionId 세션의 고유 ID
     * @param userId 사용자의 고유 ID
     * @param problemId 문제의 고유 ID
     * @return 삽입 성공 여부 (true: 성공, false: 실패)
     */
    boolean insertSessionTracker(int sessionId, int userId, int problemId);

    /**
     * 특정 세션 ID와 유저 ID에 해당하는 세션 트래커 정보를 삭제하는 메서드
     * @param sessionId 세션 고유 ID
     * @param userId 유저 고유 ID
     * @return 삭제 성공 여부 (true: 성공, false: 실패)
     */
    boolean deleteSessionTracker(int sessionId, int userId);
    
    /**
	 * 크롤러를 위한 sessiontracker 업데이트 메서드.
	 * 반복적으로 크롤링된 레코드들에 대해 업데이트가 필요한 트래커만 업데이트.
	 * @param dto 크롤링된 세션트래커 객체
	 */
    void updateSessionTracker(SessionTrackerDto dto);
}
