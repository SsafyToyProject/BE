package model.dao;

import java.sql.SQLException;
import java.util.List;
import model.dto.SessionTrackerDto;

public interface SessionTrackerDao {

    /**
     * 모든 세션 트래커 정보를 가져오는 메서드
     * @return 세션 트래커 정보를 담은 List 객체
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    List<SessionTrackerDto> getAllSessionTrackers() throws SQLException;

    /**
     * 특정 세션 ID, 유저 ID, 문제 ID에 해당하는 세션 트래커 정보를 가져오는 메서드
     * @param sessionId 세션 고유 ID
     * @param userId 유저 고유 ID
     * @param problemId 문제 고유 ID
     * @return 세션 트래커 정보를 담은 SessionTrackerDto 객체
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    SessionTrackerDto getSessionTrackerById(int sessionId, int userId, int problemId) throws SQLException;

    /**
     * 새로운 세션 트래커 정보를 삽입하는 메서드
     * @param tracker 삽입할 세션 트래커 정보를 담은 SessionTrackerDto 객체
     * @return 삽입 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    boolean insertSessionTracker(SessionTrackerDto tracker) throws SQLException;

    /**
     * 기존 세션 트래커 정보를 업데이트하는 메서드
     * @param tracker 업데이트할 세션 트래커 정보를 담은 SessionTrackerDto 객체
     * @return 업데이트 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    boolean updateSessionTracker(SessionTrackerDto tracker) throws SQLException;

    /**
     * 특정 세션 ID와 유저 ID에 해당하는 세션 트래커 정보를 삭제하는 메서드
     * @param sessionId 세션 고유 ID
     * @param userId 유저 고유 ID
     * @return 삭제 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    boolean deleteSessionTracker(int sessionId, int userId) throws SQLException;
}
