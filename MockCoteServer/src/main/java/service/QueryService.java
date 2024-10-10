package service;

import java.util.List;

import crawler.CrawlerImpl;
import model.dao.ProblemDao;
import model.dao.ProblemDaoImpl;
import model.dao.QueryDao;
import model.dao.QueryDaoImpl;
import model.dto.ProblemDto;
import model.dto.QueryDto;

public class QueryService {
	private static QueryService instance = new QueryService();
	private QueryService() {}
	public static QueryService getInstance() {return instance;}
	
	static ProblemDao problemDao = ProblemDaoImpl.getInstance();
	static QueryDao queryDao = QueryDaoImpl.getInstance();
	
	public ProblemDto getProblemInfo(int problem_id) {
		return problemDao.searchById(problem_id);
	}
	
	public List<QueryDto> getQueryList() {
		return queryDao.searchAllWithoutList();
	}
	
	public int[] insertQuery(String title, String query_str) {
		
		//크롤러 실행
		List<ProblemDto> probs = CrawlerImpl.getInstance().executeQuery(query_str);
		int num_probs = probs.size();
		if(num_probs == 0) return new int[] {0,0}; //쿼리 실패
		
		//problem 등록
		problemDao.insertProblems(probs);
		
		//QueryDto 생성
		QueryDto dto = new QueryDto(-1, title, query_str, num_probs, probs);
		queryDao.insertQuery(dto);
		
		//Query 등록
		int query_id = queryDao.insertQuery(dto);
		
		return new int[] {query_id,num_probs};
	}
}
