package com.mockcote.MockCoteServer.domain.tracker.model.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mockcote.MockCoteServer.domain.tracker.dto.SessionTracker;
import com.mockcote.MockCoteServer.domain.tracker.model.mapper.SessionTrackerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionTrackerServiceImpl implements SessionTrackerService{
	private final SessionTrackerMapper sessionTrackerMapper;
	
	 @Override
	    public SessionTracker getSessionTracker(int sessionId, int userId, int problemId) {
	        // 입력값 검증
	        if (sessionId <= 0 || userId <= 0 || problemId <= 0) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid IDs provided. All IDs must be positive.");
	        }
	        
	        try {
	            SessionTracker tracker = sessionTrackerMapper.findSessionTracker(sessionId, userId, problemId);
	            if (tracker == null) {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tracking information found for the provided IDs.");
	            }
	            return tracker;
	        } catch (Exception ex) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving session tracker data", ex);
	        }
	    }
}
