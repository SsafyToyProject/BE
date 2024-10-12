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
	
	/**
	 * 활성화된 세션들에 대해 tracker 정보를 크롤링하고 DB에 업데이트한다.
	 * servlet context listener가 주기적으로 호출한다.
	 */
	void liveTrack();
	
	/**
	 * 시작시간 5분전, 아직 셋업되지 않는 세션들의 참가자를 확정하고, 문제를 선정한다.
	 */
	void triggerTrack();
}
