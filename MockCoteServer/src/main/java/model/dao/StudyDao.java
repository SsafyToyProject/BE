package model.dao;

import model.dto.StudyDto;

public interface StudyDao {

	/**
	 * 스터디 추가하기
	 * 
	 * @param 추가할 스터디 객체
	 * @return 추가된 스터디 객체
	 */
	StudyDto addStudy(StudyDto studyDto);

	/**
	 * 스터디 검색하기
	 * 
	 * @param 검색할 스터디 name
	 * @return 검색된 스터디
	 */
	StudyDto searchStudy(String name);

	/**
	 * 스터디 수정하기
	 * 
	 * @param 수정할 스터디
	 * @return 수정된 스터디
	 */
	StudyDto updateStudy(StudyDto studyDto);

	/**
	 * 스터디 삭제하기
	 * 
	 * @param 삭제할 스터디 name
	 * @return 삭제된 스터디
	 */
	StudyDto deleteStudy(String name);
}
