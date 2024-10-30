package com.mockcote.MockCoteServer.crawler;

import java.util.List;
import java.util.Set;

import com.mockcote.MockCoteServer.domain.crawl.dto.CrawledRecord;
import com.mockcote.MockCoteServer.domain.crawl.dto.Problem;
import com.mockcote.MockCoteServer.domain.user.dto.User;

public interface Crawler {
	/**
	 * 해당 사용자가 푼 문제를 List<Integer>로 반환한다.
	 * @param users list
	 * @return 해결한 문제 List
	 */
	Set<Integer> getSolvedProblemsByUsers(List<User> users);
	
	/**
	 * solved.ac에서 문제 목록을 검색하고 검색된 문제들을 반환한다.
	 * 이 때 query는 page= 까지만 작성한다
	 * @param query string
	 * @return 크롤링된 problem Dto list
	 */
	List<Problem> executeQuery(String query);
	
	/**
	 * 해당 문제의 채점현황을 크롤링하여 각 행을 CrawledRecord 에 반환
	 * @param problem_id
	 * @return
	 */
	List<CrawledRecord> crawlTrackers(int problem_id);
}
