package model.dao;

import java.sql.SQLException;
import java.util.List;
import model.dto.SessionDto;
import model.dto.UserDto;

public interface SessionDao {
    
    /**
     * 모든 세션 정보를 가져오는 메서드
     * @return 세션 정보를 담은 List 객체
     */
    List<SessionDto> getAllSessions();
    
    /**
     * 특정 세션 ID에 해당하는 세션 정보를 가져오는 메서드
     * @param sessionId 세션 고유 ID
     * @return 세션 정보가 담긴 SessionDto 객체
     */
    SessionDto getSessionById(int sessionId);
    
    /**
     * 새로운 세션 정보를 삽입하는 메서드
     * @param session 삽입할 세션 정보를 담은 SessionDto 객체
     * @return 생성된 session_id (-1 : fail)
     */
    int insertSession(SessionDto session);

    
    /**
     * 특정 세션 ID에 해당하는 세션 정보를 삭제하는 메서드
     * @param sessionId 삭제할 세션의 고유 ID
     * @return 삭제 성공 여부 (true: 성공, false: 실패)
     */
    boolean deleteSession(int sessionId);
    
    /**
     * 현재 진행중인 세션의 정보들을 반환한다.
     * @return 현재 진행 상태의 세션 리스트
     */
    List<SessionDto> getActiveSessions();
    
    /**
     * 세션에 참가자를 추가하는 메서드
     * @param sessionId 세션 ID
     * @param userId 참가자 ID
     * @return 삽입 성공 여부 (true: 성공, false: 실패)
     */
    public boolean insertParticipant(int sessionId, int user_id);
    
    /**
     * 세션에 문제를 추가하는 메서드
     * @param sessionId 세션 ID
     * @param problemId 문제 ID
     * @return 삽입 성공 여부 (true: 성공, false: 실패)
     */
    boolean insertProblem(int sessionId, int problemId);
    
    /**
     * 시작시간 5분전이 지난 세션들 중 아직 문제가 설정되지 않는 세션들을 반환한다.
     * @return sessionDto 리스트
     */
    List<SessionDto> getReadySessions();
    
    /**
     * session_id에 해당하는 참가자들을 찾는 메서드
     * @param session_id
     * @return 참가자 user_id 리스트
     */
    List<Integer> getParticipantsById(int session_id);
    
    /**
     * 세션에 참여하는 참가자를 추가하는 메서드
     * @param user_id 유저ID
     * @param session_id 세션ID
     * @return
     */
    int addParticipant(int session_id, int user_id);
    
    /**
     * 특정 스터디에 속한 세션 정보를 가져오는 메서드
     * 
     * @param studyId 조회할 스터디의 고유 ID
     * @return 해당 스터디에 속한 세션 정보 리스트
     */
    public List<SessionDto> getSessionsByStudyId(int studyId);

    /**
     * 특정 세션에 참여하는 참가자들의 ID를 가져오는 메서드
     * 
     * @param sessionId 조회할 세션의 고유 ID
     * @return 세션에 참여한 참가자들의 user_id 리스트
     */
    public List<Integer> getParticipantsBySessionId(int sessionId);

    /**
     * 특정 세션에서 풀 문제의 ID 리스트를 가져오는 메서드
     * 
     * @param sessionId 조회할 세션의 고유 ID
     * @return 세션에서 풀 문제들의 problem_id 리스트
     */
    public List<Integer> getProblemsBySessionId(int sessionId);

    
}
