package com.mockcote.MockCoteServer.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mockcote.MockCoteServer.dto.Study;
import com.mockcote.MockCoteServer.dto.User;

public interface StudyMapper {
	
	
	 // 스터디 추가 
	int addStudy(Study studyDto);
	 
	 // 생성된 스터디ID인 스터디 멤버에 방장 추가 
	int addStudyIdAndOwnerId(@Param("studyId") int studyId, @Param("ownerId") int ownerId);
	 
	 // 스터디ID로 상세 조회 
	 Study getStudyById(int studyId);
	 
	 // 스터디ID로 스터디 멤버 리스트 받아오기 
	 List<User> getUsersByStudyId(int studyId);
	 
	
//	code로 스터디 정보 조회
	Study getStudyByCode(String code);
	
//	스터디ID로 스터디 삭제하기
	int deleteStudyById(int studyId);

//	특정 유저의 스터디 탈퇴
	int leaveStudyById(@Param("studyId") int studyId, @Param("userId") int userId);
	
	//특정 유저 스터디 가입하기
	int insertStudyMember(@Param("studyId") int studyId, @Param("userId") int userId);
}
