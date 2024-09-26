package model.dto;

import java.util.List;

public class SessionInfoDto {
	
	private List<UserDto> sessionParticipant;
	private List<ProblemDto> sessionProblem;
	
	
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
