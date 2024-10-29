package com.mockcote.MockCoteServer.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mockcote.MockCoteServer.crawler.Crawler;
import com.mockcote.MockCoteServer.dto.Problem;
import com.mockcote.MockCoteServer.dto.Session;
import com.mockcote.MockCoteServer.dto.User;
import com.mockcote.MockCoteServer.model.mapper.QueryMapper;
import com.mockcote.MockCoteServer.model.mapper.SessionMapper;
import com.mockcote.MockCoteServer.model.mapper.SessionTrackerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
	
	private final QueryMapper queryMapper;
	private final Crawler crawler;
	private final SessionMapper sessionMapper;
	private final SessionTrackerMapper sessionTrackerMapper;
	
	@Transactional
	@Override
	public void triggerSession() {
		
		// find session to trigger
		List<Session> targets = new ArrayList<>();
		List<Session> readySessions = sessionMapper.searchReadySessions();
		for(Session s : readySessions) {
			if(sessionMapper.getProblemCount(s.getSessionId()) == 0) 
				targets.add(s);
		}
		
		for(Session session : targets) {
			log.info("session triggering for id : {}",session.getSessionId());
			
			//참가자 리스트 조회
			List<String> handles = new ArrayList<>();
			for(User user : session.getSessionParticipants()) {
				handles.add(user.getHandle());
			}
			if(handles.size() == 0) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"No participants exists for session");
			}
			//solved 리스트 크롤링
			Set<Integer> solved = crawler.getSolvedProblemsByHandle(handles);
			log.info("solved len : {}", solved.size());
			
			//problemPool 파싱
			List<Integer> picks = parseProblemPool(session.getProblemPool());
			
			//problems 조회
			List<Problem> problems = queryMapper.searchCandidates(session.getQueryId());
			
			//unsolved problems 분류
			List<Problem> unsolved[] = new List[31];
			for(int i = 0; i <= 30;i++) unsolved[i] = new ArrayList<>();
			for(Problem p : problems) {
				if(!solved.contains(p.getProblemId())) {
					unsolved[p.getDifficulty()].add(p);
				}
			}
			
			//문제 선정
			List<Problem> pick_problems = new ArrayList<>();
			Random random = new Random();
			for(int diffi : picks) {
				while(diffi <= 30 && unsolved[diffi].size() == 0) diffi++;
				if(diffi > 30) {
					log.error("no problem to choose...");
					continue;
				}
				Problem pick = unsolved[diffi].get(random.nextInt(unsolved[diffi].size()));
				log.info("Pick problem {}",pick.getProblemId());
				pick_problems.add(pick);
				unsolved[diffi].remove(pick);
			}
			
			if(pick_problems.size() == 0) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"No Problem picked for session");
			}
			
			//문제 리스트 등록
			session.setSessionProblems(pick_problems);
			queryMapper.insertSessionProblems(session);
			
			//세션 트래커 등록
			sessionTrackerMapper.insertSessionTrackers(session);
			
			log.info("Session problem picked for session_id {}", session.getSessionId());
		}
	}
	
	

	
	
	/* helpers */

	private List<Integer> parseProblemPool(String problemPool) {
		List<Integer> pick_difficulties = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(problemPool);
		while (st.hasMoreTokens()) {
			int diff = 0;
			String diffi = st.nextToken();
			switch (diffi.charAt(0)) {
			case 'B':
				break;
			case 'S':
				diff += 5;
				break;
			case 'G':
				diff += 10;
				break;
			case 'P':
				diff += 15;
				break;
			case 'D':
				diff += 20;
				break;
			case 'R':
				diff += 25;
				break;
			}
			diff += 6 - (diffi.charAt(1) - '0');
			pick_difficulties.add(diff);
		}
		return pick_difficulties;
	}
}
