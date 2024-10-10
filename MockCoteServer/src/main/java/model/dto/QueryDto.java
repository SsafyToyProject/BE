package model.dto;

import java.sql.Timestamp;
import java.util.List;

public class QueryDto {
	private int query_id;
	private String title;
	private String query_str;
	private int num_problems;
	private List<ProblemDto> candidates;
	
	public QueryDto(int query_id, String title, String query_str, int num_problems, List<ProblemDto> candidates) {
		super();
		this.query_id = query_id;
		this.title = title;
		this.query_str = query_str;
		this.num_problems = num_problems;
		this.candidates = candidates;
	}
	public int getQuery_id() {
		return query_id;
	}
	public void setQuery_id(int query_id) {
		this.query_id = query_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQuery_str() {
		return query_str;
	}
	public void setQuery_str(String query_str) {
		this.query_str = query_str;
	}
	public List<ProblemDto> getCandidates() {
		return candidates;
	}
	public void setCandidates(List<ProblemDto> candidates) {
		this.candidates = candidates;
	}
	public int getNum_problems() {
		return num_problems;
	}
	public void setNum_problems(int num_problems) {
		this.num_problems = num_problems;
	}
	
}
