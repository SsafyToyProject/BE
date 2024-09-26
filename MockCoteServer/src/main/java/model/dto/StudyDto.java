package model.dto;

import java.util.List;

public class StudyDto {
	private int study_id; //스터디 고유 ID
	private int owner_id; //스터니 owner ID
	private String name; //스터디 이름
	private String description; //스터디 설명
	private String code;// 스터디 live-coding 참가 url 등을 위한 정보 코드
	
	private List<UserDto> studyMambers; //해당 스터디에 참여하는 스터디원 
	
	public int getStudy_id() {
		return study_id;
	}
	public void setStudy_id(int study_id) {
		this.study_id = study_id;
	}
	public int getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<UserDto> getStudyMambers() {
		return studyMambers;
	}
	public void setStudyMambers(List<UserDto> studyMambers) {
		this.studyMambers = studyMambers;
	}
	
	
	

}
