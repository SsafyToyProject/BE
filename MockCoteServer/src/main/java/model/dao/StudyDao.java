package model.dao;

import java.util.List;

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
	
	/**
	 * 스터디에 멤버 추가하기
	 * 
	 * @param studyId 추가할 스터디의 ID
	 * @param userId 스터디에 추가할 사용자의 ID
	 * @return 멤버 추가 성공 여부 (true: 성공, false: 실패)
	 */
	public boolean insertStudyMember(int studyId, int userId);
	
	/**
	 * 특정 스터디에 가입된 모든 사용자 ID 가져오기
	 * 
	 * @param studyId 사용자 ID를 가져올 스터디의 ID
	 * @return 스터디에 가입된 모든 사용자의 ID 리스트
	 */
	public List<Integer> getUsersByStudyId(int studyId);
	
	/**
     * 특정 스터디 ID에 해당하는 스터디 정보를 조회
     *
     * @param studyId 스터디 ID
     * @return StudyDto 객체
     */
    StudyDto getStudyById(int studyId);
    
    /**
     * 사용자가 가입한 스터디 목록을 조회
     *
     * @param userId 사용자 ID
     * @return 가입된 스터디 ID 목록
     */
    List<Integer> getStudiesByUserId(int userId);
}
