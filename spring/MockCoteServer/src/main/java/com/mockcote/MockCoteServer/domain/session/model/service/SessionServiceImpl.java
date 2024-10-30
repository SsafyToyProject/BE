package com.mockcote.MockCoteServer.domain.session.model.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mockcote.MockCoteServer.domain.session.dto.Session;
import com.mockcote.MockCoteServer.domain.session.model.mapper.SessionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
	
	private final SessionMapper sessionMapper;

	@Override
	public List<Session> getSessionsByStudyId(int studyId) {
        try {
            List<Session> sessions = sessionMapper.findSessionsByStudyId(studyId);
            if (sessions.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No sessions found for the study ID: " + studyId);
            }
            return sessions;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error while retrieving sessions", e);
        }
    }
	

}
