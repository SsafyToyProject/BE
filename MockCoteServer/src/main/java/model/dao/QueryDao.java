package model.dao;

import java.util.List;

import model.dto.QueryDto;

public interface QueryDao {
	
	/**
	 * 쿼리 객체를 DB에 삽입합니다. candidates에도 같이 삽입합니다.
	 * @param query
	 * @return generated query_id
	 */
	int insertQuery(QueryDto query);
	
	/**
	 * 모든 쿼리 객체를 반환합니다.
	 * @return QueryDto List
	 */
	List<QueryDto> searchAll();
	
	/**
	 * 모든 쿼리 객체를 반환합니다. 단 문제 목록은 포함하지 않습니다.
	 * @return 문제 리스트를 포함하지 않는 QeuryDto List
	 */
	List<QueryDto> searchAllWithoutList();
	
	/**
	 * query_id에 해당하는 쿼리 객체를 반환합니다
	 * @param query_id
	 * @return queryDto
	 */
	QueryDto searchById(int query_id);
	
	
}
