package com.mockcote.MockCoteServer.model.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mockcote.MockCoteServer.dto.Study;
import com.mockcote.MockCoteServer.dto.User;
import com.mockcote.MockCoteServer.model.mapper.StudyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

	private final StudyMapper studyMapper;

	// 스터디 생성

	@Transactional

	@Override
	public Study addStudy(Study study) { // 스터디 추가
		studyMapper.addStudy(study); 
		studyMapper.addStudyIdAndOwnerId(study.getStudyId(), study.getOwnerId()); // 추가한 스터디 멤버에 방장 추가

		return study;
	}

	// 스터디ID로 상세 조회
	@Override
	public Study getStudyById(int studyId) {
		return studyMapper.getStudyById(studyId);
	}

	// 스터디ID로 스터디 멤버 리스트 받아오기
	@Override
	public List<Map<String, Object>> getUsersByStudyId(int studyId) {
		List<User> studyMembers = studyMapper.getUsersByStudyId(studyId);

		// id와 handle만 담은 배열 만들기
		List<Map<String, Object>> memberList = new ArrayList<>();
		for (User user : studyMembers) {
			Map<String, Object> memberInfo = new LinkedHashMap<>();
			memberInfo.put("user_id", user.getUserId());
			memberInfo.put("handle", user.getHandle());
			memberList.add(memberInfo);
		}

		return memberList;
	}

//	코드로 스터디 정보 조회
	@Override
	public Study getStudyByCode(String code) {
		Study study = studyMapper.getStudyByCode(code);
//		해당 스터디가 없을 때 예외 처리
		if (study == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Study not found with invite code");
		}
		return study;
	}

//	스터디ID로 스터디 삭제
	@Transactional
	@Override
	public int deleteStudyById(int studyId) {
		int cnt = studyMapper.deleteStudyById(studyId);
//		해당 스터디가 없을 때 예외 처리
		if (cnt == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Study not found with ID");
		}
		return cnt;
	}

//	특정 유저 스터디 탈퇴
	@Transactional
	@Override
	public int leaveStudyById(int studyId, int userId) {
		int cnt = studyMapper.leaveStudyById(studyId, userId);
//		탈퇴 실패시 예외 처리
		if (cnt == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to leave study");
		}
		return cnt;
	}

}
