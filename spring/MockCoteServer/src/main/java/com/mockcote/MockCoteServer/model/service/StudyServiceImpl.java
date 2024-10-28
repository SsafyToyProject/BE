package com.mockcote.MockCoteServer.model.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
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
	@Override public Study addStudy(Study study) {
//		스터디 이름이 없다면 (400)
		if(study.getName() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Study name is null");
		}
//		스터디 추가 실패시 (500)
		try {
	        studyMapper.addStudy(study);
	        studyMapper.addStudyIdAndOwnerId(study.getStudyId(), study.getOwnerId());

	    } catch (DataAccessException ex) {
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add study", ex);
	    }
		return study;
	}

	// 스터디ID로 상세 조회
	@Override public Study getStudyById(int studyId) {
		Study study = studyMapper.getStudyById(studyId);
//		해당 스터디가 없으면 (404)
		if(study == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Study not found with study_id");
		}
		return study;
	}

	// 스터디ID로 스터디 멤버 리스트 받아오기

	@Override public List<Map<String, Object>> getUsersByStudyId(int studyId) {
		List<User> studyMembers;
//		멤버 불러오기 실패시 (500)
        try {
            studyMembers = studyMapper.getUsersByStudyId(studyId);
        } catch (DataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch study members", ex);
        }
        
//      id와 handle만 담은 list 만들기
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
		//		해당 스터디가 없으면 (404)
		if (study == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Study not found with invite code");
		}
		return study;
	}

	//	스터디ID로 스터디 삭제
	@Transactional
	@Override
	public int deleteStudyById(int studyId) {
		try {
            int cnt = studyMapper.deleteStudyById(studyId);
//          삭제한 스터디가 없을 때 (404)
            if (cnt == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Study not found with ID");
            }
            return cnt;
        } catch (DataAccessException ex) {
//        	내부 에러 (500)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete study", ex);
        }
	}

	//	특정 유저 스터디 탈퇴
	@Transactional
	@Override
	public int leaveStudyById(int studyId, int userId) {
		try {
            int cnt = studyMapper.leaveStudyById(studyId, userId);
//          탈퇴한 스터디가 없을 때
            if (cnt == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not in study or already left");
            }
            return cnt;
        } catch (DataAccessException ex) {
//        	내부 에러 (500)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to leave study", ex);
        }
	}

	@Override
	public int insertStudyMember(int studyId, int userId) {
		// TODO Auto-generated method stub
		return studyMapper.insertStudyMember(studyId, userId);
	}

}
