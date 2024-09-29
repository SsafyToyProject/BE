package model.dao;

import java.sql.SQLException;
import java.util.List;
import model.dto.SessionDto;

public interface SessionDao {
    
    /**
     * 모든 세션 정보를 가져오는 메서드
     * @return 세션 정보를 담은 List 객체
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    List<SessionDto> getAllSessions() throws SQLException;
    
    /**
     * 특정 세션 ID에 해당하는 세션 정보를 가져오는 메서드
     * @param sessionId 세션 고유 ID
     * @return 세션 정보가 담긴 SessionDto 객체
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    SessionDto getSessionById(int sessionId) throws SQLException;
    
    /**
     * 새로운 세션 정보를 삽입하는 메서드
     * @param session 삽입할 세션 정보를 담은 SessionDto 객체
     * @return 삽입 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    boolean insertSession(SessionDto session) throws SQLException;
    
    /**
     * 기존 세션 정보를 업데이트하는 메서드
     * @param session 업데이트할 세션 정보를 담은 SessionDto 객체
     * @return 업데이트 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    boolean updateSession(SessionDto session) throws SQLException;
    
    /**
     * 특정 세션 ID에 해당하는 세션 정보를 삭제하는 메서드
     * @param sessionId 삭제할 세션의 고유 ID
     * @return 삭제 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    boolean deleteSession(int sessionId) throws SQLException;
}
