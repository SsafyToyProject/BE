package com.mockcote.MockCoteServer.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mockcote.MockCoteServer.crawler.Crawler;
import com.mockcote.MockCoteServer.dto.Problem;
import com.mockcote.MockCoteServer.dto.Query;
import com.mockcote.MockCoteServer.model.mapper.ProblemMapper;
import com.mockcote.MockCoteServer.model.mapper.QueryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrawlServiceImpl implements CrawlService {
	
	private final ProblemMapper problemMapper;
	private final QueryMapper queryMapper;
	private final Crawler crawler;

	@Override
	public Problem searchProblemById(int problemId) {
		Problem ret = problemMapper.searchProblemById(problemId);
		if(ret == null) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No problem found for id : " + problemId);
		return ret;
	}

	@Override
	public int insertProblem(Problem problem) {
		return problemMapper.insertProblem(problem);
	}
	
	@Transactional
	@Override
	public Query executeQuery(Query query) {
		//크롤링
		List<Problem> crawled_problems = crawler.executeQuery(query.getQueryStr());
		if(crawled_problems == null)
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Crawling error");
		
		//Problems table 삽입
		List<Integer> problems = new ArrayList<>();
		for(Problem p : crawled_problems) {
			problemMapper.insertProblem(p);
			problems.add(p.getProblemId());
		}
		
		//queries table 삽입
		queryMapper.insertQuery(query); //queryId 할당됨
		query.setNumProblems(problems.size());
		query.setProblems(problems);
		
		//candidates table 삽입
		queryMapper.insertCandidates(query);
		
		return query;
	}

	@Override
	public List<Query> searchQueriesWithoutProblems() {
		List<Query> list = queryMapper.searchAllWithoutProblems();
		if(list == null)
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
		return list;
	}
	

}
