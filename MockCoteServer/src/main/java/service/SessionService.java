package service;

import java.util.ArrayList;
import java.util.List;

import model.dao.ProblemDao;
import model.dao.ProblemDaoImpl;
import model.dao.SessionDao;
import model.dao.SessionDaoImpl;
import model.dao.UserDao;
import model.dao.UserDaoImpl;
import model.dto.ProblemDto;
import model.dto.SessionDto;
import model.dto.UserDto;

public class SessionService {
	private static SessionService instance = new SessionService();
	private SessionService() {}
	public static SessionService getInstance() {return instance;}
	
	private SessionDao sessionDao = SessionDaoImpl.getInstance();
	private UserDao userDao = UserDaoImpl.getInstance();
	private ProblemDao problemDao = ProblemDaoImpl.getInstance();
	
	/**
	 * 
	 * @param session 정보 Dto
	 * @return generated session id, (-1 : fail)
	 */
	public int RegisterSession(SessionDto session) {
		//sessions 테이블에 삽입
		int session_id = sessionDao.insertSession(session);
		if(session_id == -1) return -1;
		session.setSession_id(session_id);
		
		return session_id;
	}
	
	 /**
     * 세션에 참가자 추가 (중복 확인 포함)
     * 
     * @param session_id 세션 ID
     * @param user_id 사용자 ID
     * @return 성공 시 1, 중복 시 -1 반환
     */
    public int addParticipant(int session_id, int user_id) {
        // session_participants 테이블에 참가자 추가
        return sessionDao.addParticipant(session_id, user_id);
    }
	
	/**
     * 특정 스터디에 속한 모든 세션 가져오기
     * 
     * @param studyId 스터디 ID
     * @return 세션 정보 리스트
     */
    public List<SessionDto> getSessionsByStudyId(int studyId) {
        return sessionDao.getSessionsByStudyId(studyId);
    }

    /**
     * 특정 세션에 참가한 모든 사용자 가져오기
     * 
     * @param sessionId 세션 ID
     * @return 해당 세션의 사용자 ID 리스트
     */
    public List<Integer> getSessionParticipants(int sessionId) {
        return sessionDao.getParticipantsBySessionId(sessionId);
    }

    /**
     * 특정 세션에서 풀 문제 리스트 가져오기
     * 
     * @param sessionId 세션 ID
     * @return 해당 세션에서 풀 문제 ID 리스트
     */
    public List<Integer> getSessionProblems(int sessionId) {
        return sessionDao.getProblemsBySessionId(sessionId);
    }
    
    /**
     * 세션 ID에 해당하는 세션 상세 정보 가져오기 (문제 및 참가자 정보 포함)
     * 
     * @param sessionId
     * @return sessionDto
     */
    public SessionDto getSessionById(int sessionId) {
    	SessionDto ret = sessionDao.getSessionById(sessionId);
    	
    	List<Integer> uids = sessionDao.getParticipantsById(sessionId);
    	List<UserDto> users = new ArrayList<>();
    	for(int uid : uids) users.add(userDao.searchUserById(uid));
    	ret.setSessionParticipant(users);
    	
    	List<Integer> pids = sessionDao.getProblemsBySessionId(sessionId);
    	List<ProblemDto> probs = new ArrayList<>();
    	for(int pid : pids) probs.add(problemDao.searchById(pid));
    	ret.setSessionProblem(probs);
    	
    	return ret;
    }
	
	
}
