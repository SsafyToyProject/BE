package com.mockcote.MockCoteServer.domain.study.model.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mockcote.MockCoteServer.domain.study.dto.CreateStudyResponse;
import com.mockcote.MockCoteServer.domain.study.dto.Study;
import com.mockcote.MockCoteServer.domain.study.model.mapper.StudyMapper;
import com.mockcote.MockCoteServer.domain.user.dto.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

	private final StudyMapper studyMapper;

	// 스터디 생성
	@Transactional
	@Override
	public CreateStudyResponse addStudy(CreateStudyResponse study) {
		//		스터디 이름이 없다면 (400)
		if(study.getName() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Study name is null");
		}
		// 		code 생성 (랜덤 문자열 생성)
		study.setCode(generateStudyCode()); 

		studyMapper.addStudy(study);
		studyMapper.insertStudyMember(study.getStudyId(), study.getOwnerId());
		return study;
	}

	// 스터디ID로 상세 조회
	@Override
	public Study getStudyById(int studyId) {
		Study study = studyMapper.getStudyById(studyId);
		//	해당 스터디가 없으면 (404)
		if(study == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Study not found with study_id");
		}
		
//		해당 스터디에 가입한 유저 정보
		List<User> studyMembers = studyMapper.getUsersByStudyId(studyId);

		// id와 handle만 담은 list 만들기
		List<Map<String, Object>> memberList = new ArrayList<>();
		for (User user : studyMembers) {
			Map<String, Object> memberInfo = new LinkedHashMap<>();
			memberInfo.put("user_id", user.getUserId());
			memberInfo.put("handle", user.getHandle());
			memberList.add(memberInfo);
		}
//		스터디 멤버 필드에 추가
		study.setStudyMembers(memberList);
		return study;
	}

	//	코드로 스터디 정보 조회
	@Override
	public Study getStudyByCode(String code) {
		Study study = studyMapper.getStudyByCode(code);
		//		Optional<Study> optStudy = Optional.ofNullable(study);
		//		optStudy.orElseThrow(()-> new ResponseStatusException());
		// 해당 스터디가 없으면 (404)
		if (study == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Study not found with invite code");
		}
		return study;
	}

	// 스터디ID로 스터디 삭제
	@Transactional
	@Override
	public int deleteStudyById(int studyId) {
		int cnt = studyMapper.deleteStudyById(studyId);
		// 삭제한 스터디가 없을 때 (404)
		if (cnt == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Study not found with ID");
		}
		return cnt;
	}

	// 특정 유저 스터디 탈퇴
	@Transactional
	@Override
	public int leaveStudyById(int studyId, int userId) {
	    // 탈퇴하기
		int cnt = studyMapper.leaveStudyById(studyId, userId);
		// 탈퇴한 스터디가 없을 때
		if (cnt == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not in study or already left");
		}
		return cnt;
	}

	@Override
	public int insertStudyMember(int studyId, int userId) {
		// 입력 값 검증
		if (studyId <= 0 || userId <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid study ID or user ID");
		}
		int rowsAffected = studyMapper.insertStudyMember(studyId, userId);
		//추가된 값이 없을 때
		if (rowsAffected == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No changes made. User may already be in study or study does not exist.");
		}
		return rowsAffected;
	}

	@Override
	public List<Study> getStudiesByUserId(int userId) {
		// 입력 값 검증
		if (userId <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user ID");
		}
		List<Study> studies = studyMapper.getStudiesByUserId(userId);
		if (studies.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No studies found for user ID: " + userId);
		}
		return studies;
	}

	// 스터디장 확인
	@Override
	public boolean checkStudyByOwner(int userId) {
//		userId가 스터디장인 스터디 조회하기
		List<Integer> list = studyMapper.getStudyByOwner(userId);
//		있다면 true
		if(!list.isEmpty()) return true;
//		없으면 false
		return false;
	}
	
	// 스터디 방장 위임
	@Transactional
	@Override
	public int setStudyOwner(int studyId, int userId) {
		int cnt = studyMapper.setStudyOwner(studyId, userId);
		return cnt;
	}

	// 랜덤한 스터디 code를 생성하는 메서드
	private String generateStudyCode() {
		return Long.toHexString(Double.doubleToLongBits(Math.random()));
	}
}
