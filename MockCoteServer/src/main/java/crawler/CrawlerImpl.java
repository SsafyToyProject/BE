package crawler;

import java.util.List;

import model.ProblemDto;

public class CrawlerImpl implements Crawler {
	
	//singleton
	static Crawler instance = new CrawlerImpl();
	static Crawler getInstance() {return instance;}
	private CrawlerImpl() {}
	
	@Override
	public List<Integer> getSolvedProblemsByHandle(String handle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProblemDto> getProblemsByQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
