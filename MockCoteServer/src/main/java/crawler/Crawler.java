package crawler;

import java.util.List;
import java.util.Set;

import model.dto.ProblemDto;
import model.dto.QueryDto;

public interface Crawler {
	/**
	 * 해당 사용자가 푼 문제를 List<Integer>로 반환한다.
	 * @param handles
	 * @return 해결한 문제 List
	 */
	Set<Integer> getSolvedProblemsByHandle(List<String> handles);
	
	/**
	 * solved.ac에서 문제 목록을 검색하고 검색된 문제들을 반환한다.
	 * 이 때 query는 page= 까지만 작성한다
	 * @param query string
	 * @return 크롤링된 problem Dto list
	 */
	List<ProblemDto> executeQuery(String query);
	
	
	//TODO : 라이브 코딩 트래킹 메소드
}
