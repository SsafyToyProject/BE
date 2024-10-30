package com.mockcote.MockCoteServer.domain.tracker.model.service;

import com.mockcote.MockCoteServer.domain.tracker.dto.SessionTracker;

public interface SessionTrackerService {
	SessionTracker getSessionTracker(int sessionId, int userId, int problemId);
}
