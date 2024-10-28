package com.mockcote.MockCoteServer.model.service;

import java.util.List;
import java.util.Map;

import com.mockcote.MockCoteServer.dto.Study;

public interface StudyService {
	
//	스터디 생성
	Study addStudy(Study study);
	
//	스터디ID로 상세조회
	Study getStudyById(int studyId);

//	스터디ID로 스터디 멤버 리스트 받아오기
	List<Map<String, Object>> getUsersByStudyId(int studyId);
	
//	코드로 스터디 정보 조회
	Study getStudyByCode(String code);
	
//	스터디ID로 스터디 삭제
	int deleteStudyById(int studyId);

//	특정 유저 스터디 탈퇴
	int leaveStudyById(int studyId, int userId);

}
