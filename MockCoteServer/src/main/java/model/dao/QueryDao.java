package model.dao;

import java.util.List;

import model.dto.QueryDto;

public interface QueryDao {
	
	/**
	 * 쿼리 객체를 DB에 삽입합니다. candidates에도 같이 삽입합니다.
	 * @param query
	 */
	void insertQuery(QueryDto query);
	
	/**
	 * 모든 쿼리객체를 반환합니다.
	 * @return QueryDto List
	 */
	List<QueryDto> searchAll();
	
	/**
	 * query_id에 해당하는 쿼리 객체를 반환합니다
	 * @param query_id
	 * @return queryDto
	 */
	QueryDto searchById(int query_id);
	
	
}
