package model.dto;

import java.util.List;

public class StudyDto {
	private int study_id;
	private int owner_id;
	private String name;
	private String description;
	private String code;
	
	private List<UserDto> studyMambers;
	
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
