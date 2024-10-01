package model.dto;

import java.sql.Timestamp;

public class SessionTrackerDto {
	private int session_id;
	private int user_id;
	private int problem_id;
	private Timestamp solved_at;
	private int performance;
	private String language;
	private String code_link;
	private String description;
	
	/**
	 * 
	 * @param session_id 세션 고유 ID
	 * @param user_id 유저 ID
	 * @param problem_id 문제 ID
	 * @param solved_at 문제를 푼 시각
	 * @param performance 문제 실행시간
	 * @param language 문제 풀이 언어
	 * @param code_link 문제 코드 백준 링크
	 * @param description 문제 설명/요약
	 */
	public SessionTrackerDto(int session_id, int user_id, int problem_id, Timestamp solved_at, int performance,
			String language, String code_link, String description) {
		super();
		this.session_id = session_id;
		this.user_id = user_id;
		this.problem_id = problem_id;
		this.solved_at = solved_at;
		this.performance = performance;
		this.language = language;
		this.code_link = code_link;
		this.description = description;
	}
	
	public int getSession_id() {
		return session_id;
	}
	public void setSession_id(int session_id) {
		this.session_id = session_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getProblem_id() {
		return problem_id;
	}
	public void setProblem_id(int problem_id) {
		this.problem_id = problem_id;
	}
	public Timestamp getSolved_at() {
		return solved_at;
	}
	public void setSolved_at(Timestamp solved_at) {
		this.solved_at = solved_at;
	}
	public int getPerformance() {
		return performance;
	}
	public void setPerformance(int performance) {
		this.performance = performance;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCode_link() {
		return code_link;
	}
	public void setCode_link(String code_link) {
		this.code_link = code_link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}