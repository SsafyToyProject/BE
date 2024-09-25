package model.dto;

import java.sql.Timestamp;
import java.util.List;

public class QueryDto {
	private int query_id;
	private String title;
	private String query_str;
	private List<ProblemDto> candidates;
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
}
