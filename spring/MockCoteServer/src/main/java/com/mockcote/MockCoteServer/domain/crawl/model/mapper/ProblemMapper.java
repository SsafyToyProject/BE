package com.mockcote.MockCoteServer.domain.crawl.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mockcote.MockCoteServer.domain.crawl.dto.Problem;

@Mapper
public interface ProblemMapper {
	
	/**
	 * 문제 정보 조회
	 * @param problemId
	 * @return problem
	 */
	Problem searchProblemById(int problemId);
	
	/**
	 * 문제 삽입. 이미 존재하는 문제라면 난이도만 업데이트
	 * @param problem list
	 * @return affected row
	 */
	int insertProblems(List<Problem> problem);
}
