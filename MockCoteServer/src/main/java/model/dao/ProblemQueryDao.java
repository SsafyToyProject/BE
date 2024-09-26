package model.dao;

import java.util.List;

import model.dto.ProblemDto;
import model.dto.QueryDto;

/**
 * problem crawling에 필요한 DAO
 */
public interface ProblemQueryDao {
	
	/**
	 * 미리 등록된 쿼리 들을 전부 반환한다. 반환값에 문제 목록은 포함하지 않는다.
	 * @return candidates가 포함되지 않는 QueryDto의 List
	 */
	List<QueryDto> searchAllPresetQueries();
	
	/**
	 * query_id에 맞는 QueryDto를 반환한다. 반환값에 문제 목록이 포함된다.
	 * @param query_id query_id
	 * @return 문제 목록이 포함된 프리셋 QueryDto
	 */
	QueryDto getQueryById(int query_id);
	
	/**
	 * 새로운 쿼리 프리셋을 등록한다. 크롤링을 진행하고 실제 DB에 적용한 후 결과를 반환한다.
	 * @param title 프리셋의 이름
	 * @param query_str 프리셋의 실제 쿼리 스트링
	 * @return 생성된 query_id, 0: 실패
	 */
	int addNewQuery(String title, String query_str);
	
	/**
	 * 라이브 코딩 세션에 사용할 문제를 선정한다.
	 * @param query_id 사용할 쿼리 프리셋의 id
	 * @param handles 참가자들의 handle 목록
	 * @param levels 선정할 문제의 레벨들
	 * @return 선정된 문제들의 리스트
	 */
	List<ProblemDto> pickProblems(int query_id, List<String> handles, List<Integer> levels);
	
}
