package service;

import model.dto.SessionDto;

public class SessionServiceImpl implements SessionService {

	private static SessionService instance = new SessionServiceImpl();
	private SessionServiceImpl() {}
	public static SessionService getInstance() {return instance;}
	@Override
	public void addSession(SessionDto dto) {
		// TODO Auto-generated method stub

	}

}
