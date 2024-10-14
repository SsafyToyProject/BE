package service;

import java.util.ArrayList;
import java.util.List;

import model.dao.SessionTrackerDao;
import model.dao.SessionTrackerDaoImpl;
import model.dto.SessionTrackerDto;

public class SessionTrackerService {
    private static SessionTrackerService instance = new SessionTrackerService();

    private SessionTrackerDao sessionTrackerDao = SessionTrackerDaoImpl.getInstance();

    private SessionTrackerService() {}

    public static SessionTrackerService getInstance() {
        return instance;
    }

    /**
     * 세션 트래커 정보를 가져오는 메서드
     *
     * @param sessionId 세션 ID
     * @param userId 유저 ID
     * @param problemId 문제 ID
     * @return SessionTrackerDto 리스트
     */
    public List<SessionTrackerDto> getSessionTrackers(int sessionId, int userId, int problemId) {
        // 특정 세션 ID, 유저 ID, 문제 ID에 해당하는 SessionTracker를 가져옴
        List<SessionTrackerDto> trackers = new ArrayList<>();
        SessionTrackerDto tracker = sessionTrackerDao.getSessionTrackerById(sessionId, userId, problemId);
        if (tracker != null) {
            trackers.add(tracker);
        }
        return trackers;
    }
    
    
}
