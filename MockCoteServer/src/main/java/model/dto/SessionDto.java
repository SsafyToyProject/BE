package model.dto;

import java.sql.Timestamp;
import java.util.List;

public class SessionDto {
	private int session_id; 
	private int study_id; 
	private Timestamp start_at;
	private Timestamp end_at;
	
	private List<UserDto> sessionParticipant;
	private List<ProblemDto> sessionProblem;
	
	

	/**
	 * 
	 * @param session_id 라이브코딩 세션 고유 ID
	 * @param study_id 라이브 코딩 참가 스터디 ID
	 * @param start_at 라이브코딩 시작 시간
	 * @param end_at 라이브코딩 끝나는 시간
	 */
	public SessionDto(int session_id, int study_id, Timestamp start_at, Timestamp end_at) {
		super();
		this.session_id = session_id;
		this.study_id = study_id;
		this.start_at = start_at;
		this.end_at = end_at;
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