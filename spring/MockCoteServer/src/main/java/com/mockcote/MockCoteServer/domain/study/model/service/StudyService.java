package com.mockcote.MockCoteServer.domain.study.model.service;

import java.util.List;
import java.util.Map;

import com.mockcote.MockCoteServer.domain.study.dto.Study;

public interface StudyService {
	
	/**
     * 신규 스터디 생성
     * @param study
     * @return study
     */
	Study addStudy(Study study);
	
	/**
     * 스터디ID로 스터디 정보 조회
     * @param studyId
     * @return study
     */
	Study getStudyById(int studyId);

	/**
     * 스터디ID로 스터디 멤버 리스트 조회
     * @param studyId
     * @return List (멤버의 id와 handle을 갖고있음)
     */
	List<Map<String, Object>> getUsersByStudyId(int studyId);
	
	/**
     * 초대코드로 스터디 정보 조회
     * @param code
     * @return study
     */
	Study getStudyByCode(String code);
	
	/**
     * 스터디ID로 스터디 삭제
     * @param studyId
     * @return int
     */
	int deleteStudyById(int studyId);

	/**
     * 특정 유저의 스터디 탈퇴
     * @param studyId(탈퇴할 스터디), userId(탈퇴할 유저)
     * @return int
     */
	int leaveStudyById(int studyId, int userId);
	
	/**
	 * 특정 스터디에 유저 가입
	 * @param studyId
	 * @param userId
	 * @return
	 */
	int insertStudyMember(int studyId, int userId);
	
	/**
	 * userId로 특정 유저가 가입한 모든 스터디 조회
	 * @param userId
	 * @return
	 */
	public List<Study> getStudiesByUserId(int userId);
	

}
