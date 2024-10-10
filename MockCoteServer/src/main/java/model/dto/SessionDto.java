package model.dto;

import java.sql.Timestamp;
import java.util.List;

public class SessionDto {
	private int session_id; 
	private int study_id; 
	private int query_id;
	private Timestamp start_at;
	private Timestamp end_at;
	private String problem_pool;
	
	private List<UserDto> sessionParticipant;
	private List<ProblemDto> sessionProblem;
	
	
	public SessionDto(int session_id, int study_id, int query_id, Timestamp start_at, Timestamp end_at,
			String problem_pool) {
		super();
		this.session_id = session_id;
		this.study_id = study_id;
		this.query_id = query_id;
		this.start_at = start_at;
		this.end_at = end_at;
		this.problem_pool = problem_pool;
	}
	public int getSession_id() {
		return session_id;
	}
	public void setSession_id(int session_id) {
		this.session_id = session_id;
	}
	public int getStudy_id() {
		return study_id;
	}
	public void setStudy_id(int study_id) {
		this.study_id = study_id;
	}
	public Timestamp getStart_at() {
		return start_at;
	}
	public void setStart_at(Timestamp start_at) {
		this.start_at = start_at;
	}
	public Timestamp getEnd_at() {
		return end_at;
	}
	public void setEnd_at(Timestamp end_at) {
		this.end_at = end_at;
	}
	
	
	public int getQuery_id() {
		return query_id;
	}
	public void setQuery_id(int query_id) {
		this.query_id = query_id;
	}
	public String getProblem_pool() {
		return problem_pool;
	}
	public void setProblem_pool(String problem_pool) {
		this.problem_pool = problem_pool;
	}
	public List<UserDto> getSessionParticipant() {
		return sessionParticipant;
	}



	public void setSessionParticipant(List<UserDto> sessionParticipant) {
		this.sessionParticipant = sessionParticipant;
	}



	public List<ProblemDto> getSessionProblem() {
		return sessionProblem;
	}



	public void setSessionProblem(List<ProblemDto> sessionProblem) {
		this.sessionProblem = sessionProblem;
	}



}