package com.mockcote.MockCoteServer.model.mapper;

import java.util.List;

import com.mockcote.MockCoteServer.dto.Query;

public interface QueryMapper {
	
	/**
	 * Query정보 삽입
	 * @param query
	 * @return affected row
	 */
    int insertQuery(Query query);
    
    /**
     * Query에 포함된 문제 리스트 candidates에 삽입
     * @param query
     * @return affected row
     */
    int insertCandidates(Query query);
    
    /**
     * problem list가 포함된 query 리스트 조회
     * @return list query
     */
    List<Query> searchAllWithProblems();

    /**
     * problem list가 포함되지 않은 query 리스트 조회
     * @return list query
     */
    List<Query> searchAllWithoutProblems();
}
