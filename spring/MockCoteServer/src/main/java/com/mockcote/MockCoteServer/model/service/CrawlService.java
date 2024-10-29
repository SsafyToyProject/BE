package com.mockcote.MockCoteServer.model.service;

import java.util.List;

import com.mockcote.MockCoteServer.dto.Problem;
import com.mockcote.MockCoteServer.dto.Query;

public interface CrawlService {
	
	/**
	 * 문제 정보 조회
	 * @param problemId
	 * @return problem
	 */
	Problem searchProblemById(int problemId);
	
	/**
	 * 문제 삽입
	 * @param problem
	 * @return affected row
	 */
	int insertProblem(Problem problem);
	
	/**
	 * 쿼리를 크롤링하여 쿼리정보 DB에 업데이트
	 * @param query
	 * @return 완성된 query
	 */
	Query executeQuery(Query query);
	
	/**
	 * 문제 리스트를 포함하지 않는 쿼리 조회
	 * @return list query
	 */
	List<Query> searchQueriesWithoutProblems();
	
	/**
	 * 시작 시간이 3분 미만으로 남은 세션들에 문제를 선정하고,
	 * 참가자와 문제에 해당하는 sessionTracker를 삽입함
	 */
	void triggerSession();
}
