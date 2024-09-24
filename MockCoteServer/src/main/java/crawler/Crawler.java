package crawler;

import java.util.List;

import model.ProblemDto;

public interface Crawler {
	/**
	 * 해당 사용자가 푼 문제를 List<Integer>로 반환한다
	 * @param handle
	 * @return 해결한 문제 List
	 */
	List<Integer> getSolvedProblemsByHandle(String handle);
	
	/**
	 * solved.ac에서 문제 목록을 검색하고 검색된 문제들을 Problems에 최신화한 다음 preQueried 테이블과 Candidates 테이블에 삽입한다.
	 * @param query
	 */
	void getProblemsByQuery(String query);
	
	void updateCandidates();
	
	//TODO : 라이브 코딩 트래킹 메소드
}
