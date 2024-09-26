package model.dao;

import java.util.List;

import model.dto.SessionDto;
import model.dto.SessionTrackerDto;

public class LiveSessionDaoImpl implements LiveSessionDao {

	//singleton
	private static LiveSessionDao instance = new LiveSessionDaoImpl();
	private LiveSessionDaoImpl() {}
	public static LiveSessionDao getInstance() {return instance;}
	
	@Override
	public List<SessionDto> getActiveSessions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SessionTrackerDto> getTrackersBySessionId(int session_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSession(SessionDto dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTracker(SessionTrackerDto dto) {
		// TODO Auto-generated method stub

	}

}
