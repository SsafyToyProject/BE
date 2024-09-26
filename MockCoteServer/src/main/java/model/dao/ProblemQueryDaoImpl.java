package model.dao;

import java.util.List;

import model.dto.ProblemDto;
import model.dto.QueryDto;

public class ProblemQueryDaoImpl implements ProblemQueryDao {
	
	//singleton
	private static ProblemQueryDao instance = new ProblemQueryDaoImpl();
	private ProblemQueryDaoImpl() {}
	public static ProblemQueryDao getInstance() {return instance;}
	
	@Override
	public List<QueryDto> searchAllPresetQueries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryDto getQueryById(int query_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addNewQuery(String title, String query_str) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ProblemDto> pickProblems(int query_id, List<String> handles, List<Integer> levels) {
		// TODO Auto-generated method stub
		return null;
	}

}
