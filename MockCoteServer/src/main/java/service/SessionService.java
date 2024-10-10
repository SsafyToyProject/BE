package service;

import java.util.List;

import model.dao.SessionDao;
import model.dao.SessionDaoImpl;
import model.dto.SessionDto;

public class SessionService {
	private static SessionService instance = new SessionService();
	private SessionService() {}
	public static SessionService getInstance() {return instance;}
	
	private SessionDao sessionDao = SessionDaoImpl.getInstance();
	
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
     * 세션에 참가자 추가
     * 
     * @param session_id 세션 ID
     * @param user_id 사용자 ID
     * @return 성공 시 1, 실패 시 -1 반환
     */
	public int addParticipant(int session_id, int user_id) {
		// session_participants 테이블에 삽입
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
	
	
}
