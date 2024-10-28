package com.mockcote.MockCoteServer.model.mapper;

import com.mockcote.MockCoteServer.dto.Problem;

public interface ProblemMapper {
	
	/**
	 * 문제 정보 조회
	 * @param problemId
	 * @return problem
	 */
	Problem searchProblemById(int problemId);
	
	/**
	 * 문제 삽입. 이미 존재하는 문제라면 난이도만 업데이트
	 * @param problem
	 * @return affected row
	 */
	int insertProblem(Problem problem);
}
