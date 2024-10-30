package com.mockcote.MockCoteServer.domain.session.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mockcote.MockCoteServer.domain.crawl.dto.Problem;
import com.mockcote.MockCoteServer.domain.session.dto.Session;
import com.mockcote.MockCoteServer.domain.session.model.service.SessionService;
import com.mockcote.MockCoteServer.domain.user.dto.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

  //GET: /session/study/{study-id}
    @GetMapping("/study/{study-id}")
    public ResponseEntity<Map<String, Object>> getSessionsByStudyId(@PathVariable("study-id") int studyId) {
        List<Session> sessions = sessionService.getSessionsByStudyId(studyId);

        if (sessions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Map<String, Object>> sessionDetails = new ArrayList<>();
        for (Session session : sessions) {
            Map<String, Object> sessionInfo = new LinkedHashMap<>();
            sessionInfo.put("session_id", session.getSessionId());
            sessionInfo.put("query_id", session.getQueryId());
            sessionInfo.put("start_at", session.getStartAt().toString());
            sessionInfo.put("end_at", session.getEndAt().toString());
            sessionInfo.put("problem_pool", session.getProblemPool());

            List<User> participants = session.getSessionParticipants();
            List<Map<String, Object>> participantDetails = new ArrayList<>();
            if (participants != null) {
                for (User participant : participants) {
                    Map<String, Object> participantInfo = new LinkedHashMap<>();
                    participantInfo.put("user_id", participant.getUserId());
                    participantInfo.put("handle", participant.getHandle());
                    participantDetails.add(participantInfo);
                }
            }
            sessionInfo.put("participants_cnt", participantDetails.size());
            sessionInfo.put("session_participants", participantDetails);

            List<Problem> problems = session.getSessionProblems();
            List<Map<String, Object>> problemDetails = new ArrayList<>();
            if (problems != null) {
                for (Problem problem : problems) {
                    Map<String, Object> problemInfo = new LinkedHashMap<>();
                    problemInfo.put("problem_id", problem.getProblemId());
                    problemDetails.add(problemInfo);
                }
            }
            sessionInfo.put("problems_cnt", problemDetails.size());
            sessionInfo.put("session_problems", problemDetails);

            sessionDetails.add(sessionInfo);
        }

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("num_elements", sessions.size());
        responseBody.put("sessions", sessionDetails);
        

        return ResponseEntity.ok(responseBody);
    }

}
