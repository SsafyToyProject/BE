package model.dao;

import java.util.List;

import model.dto.ProblemDto;

public interface ProblemDao {
	/**
	 * Problem List를 추가합니다. 중복된 문제는 난이도를 업데이트합니다.
	 * @param problemList
	 * @return affected_row
	 */
	int insertProblems(List<ProblemDto> problemList);
	
	/**
	 * ProblemDto을 업데이트합니다
	 * @param problem
	 * @return affected_row;
	 */
	int updateProblem(ProblemDto problem);
	
	/**
	 * problem_id에 해당하는 문제를 반환합니다.
	 * @param problem_id
	 * @return problemDto
	 */
	ProblemDto searchById(int problem_id);
	
}
