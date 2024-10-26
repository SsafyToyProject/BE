package com.mockcote.MockCoteServer.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mockcote.MockCoteServer.dto.Study;
import com.mockcote.MockCoteServer.model.mapper.StudyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {
	
	private final StudyMapper studyMapper;
	
	/*
	 * // 스터디 생성
	 * 
	 * @Transactional
	 * 
	 * @Override public Study addStudy(Study study) { // 스터디 추가
	 * studyMapper.addStudy(study); // 추가한 스터디 멤버에 방장 추가
	 * studyMapper.addStudyIdAndOwnerId(study.getStudyId(), study.getOwnerId());
	 * 
	 * return study; }
	 * 
	 * // 스터디ID로 상세 조회
	 * 
	 * @Override public Study getStudyById(int studyId) { return
	 * studyMapper.getStudyById(studyId); }
	 * 
	 * // 스터디ID로 스터디 멤버 리스트 받아오기
	 * 
	 * @Override public List<Map<String, Object>> getUsersByStudyId(int studyId) {
	 * List<User> studyMembers = studyMapper.getUsersByStudyId(studyId);
	 * 
	 * // id와 handle만 담은 배열 만들기 List<Map<String, Object>> memberList = new
	 * ArrayList<>(); for(User user : studyMembers) { Map<String, Object> memberInfo
	 * = new LinkedHashMap<>(); memberInfo.put("user_id", user.getUserId());
	 * memberInfo.put("handle", user.getHandle()); memberList.add(memberInfo); }
	 * 
	 * return memberList; }
	 */
	
//	코드로 스터디 정보 조회
	@Override
	public Study getStudyByCode(String code) {
		return studyMapper.getStudyByCode(code);
	}

//	스터디ID로 스터디 삭제
	@Transactional
	@Override
	public int deleteStudyById(int studyId) {
		return studyMapper.deleteStudyById(studyId);
	}

//	특정 유저 스터디 탈퇴
	@Transactional
	@Override
	public int leaveStudyById(int studyId, int userId) {
		return studyMapper.leaveStudyById(studyId, userId);
	}

}
