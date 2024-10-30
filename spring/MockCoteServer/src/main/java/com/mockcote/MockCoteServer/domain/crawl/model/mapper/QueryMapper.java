package com.mockcote.MockCoteServer.domain.crawl.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mockcote.MockCoteServer.domain.crawl.dto.Problem;
import com.mockcote.MockCoteServer.domain.crawl.dto.Query;
import com.mockcote.MockCoteServer.domain.session.dto.Session;

@Mapper
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
    
    /**
     * 쿼리에 포함된 문제 리스트 조회
     * @param query_id
     * @return problem list
     */
    List<Problem> searchCandidates(int query_id);
    
    /**
     * Session_problems 테이블에 problems 삽입
     * @param session
     * @return affected row
     */
    int insertSessionProblems(Session session);
}
