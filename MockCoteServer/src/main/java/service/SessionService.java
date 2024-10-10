package service;

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
	
	public int addParticipant(int session_id, int user_id) {
		// session_participants 테이블에 삽입
		return sessionDao.addParticipant(session_id, user_id);
	}
	
	
}
