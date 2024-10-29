package com.mockcote.MockCoteServer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mockcote.MockCoteServer.dto.SessionTracker;
import com.mockcote.MockCoteServer.model.service.SessionTrackerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tracker")
@RequiredArgsConstructor
public class SessionTrackerController {
    private final SessionTrackerService sessionTrackerService;

  //GET: /tracker/info/{session-id}/{user-id}/{problem-id}
    @GetMapping("/info/{session-id}/{user-id}/{problem-id}")
    public ResponseEntity<SessionTracker> getTrackerInfo(@PathVariable("session-id") int sessionId,
                                                         @PathVariable("user-id") int userId,
                                                         @PathVariable("problem-id") int problemId) {
        SessionTracker tracker = sessionTrackerService.getSessionTracker(sessionId, userId, problemId);
        if (tracker == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tracker found for the provided ids.");
        }
        return ResponseEntity.ok(tracker);
    }
}
