package com.mockcote.MockCoteServer.model.service;

import org.springframework.stereotype.Service;

import com.mockcote.MockCoteServer.dto.SessionTracker;
import com.mockcote.MockCoteServer.model.mapper.SessionTrackerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionTrackerServiceImpl implements SessionTrackerService{
	private final SessionTrackerMapper sessionTrackerMapper;
	
	@Override
    public SessionTracker getSessionTracker(int sessionId, int userId, int problemId) {
        return sessionTrackerMapper.findSessionTracker(sessionId, userId, problemId);
    }
}
