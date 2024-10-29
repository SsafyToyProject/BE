package com.mockcote.MockCoteServer.model.service;

import com.mockcote.MockCoteServer.dto.SessionTracker;

public interface SessionTrackerService {
	SessionTracker getSessionTracker(int sessionId, int userId, int problemId);
}
