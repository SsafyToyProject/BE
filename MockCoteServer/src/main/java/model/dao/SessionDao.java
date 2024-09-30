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
     * 특정 세션 ID에 해당하는 세션 정보를 삭제하는 메서드
     * @param sessionId 삭제할 세션의 고유 ID
     * @return 삭제 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    boolean deleteSession(int sessionId) throws SQLException;
    
    /**
     * 현재 진행중인 세션의 정보들을 반환한다.
     * @return 현재 진행 상태의 세션 리스트
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    List<SessionDto> getActiveSessions() throws SQLException;
    
    /**
     * 세션에 참가자를 추가하는 메서드
     * @param sessionId 세션 ID
     * @param userId 참가자 ID
     * @return 삽입 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    boolean insertParticipant(int sessionId, int userId) throws SQLException;
    
    /**
     * 세션에 문제를 추가하는 메서드
     * @param sessionId 세션 ID
     * @param problemId 문제 ID
     * @return 삽입 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException 데이터베이스 오류 발생 시 예외 처리
     */
    boolean insertProblem(int sessionId, int problemId) throws SQLException;
}
