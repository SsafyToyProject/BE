package com.mockcote.MockCoteServer.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.mockcote.MockCoteServer.dto.Study;
import com.mockcote.MockCoteServer.dto.User;
import com.mockcote.MockCoteServer.model.service.StudyService;
import com.mockcote.MockCoteServer.model.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {
	
    private final StudyService studyService;
    private final UserService userService;    
	
//	스터디 생성
    @PostMapping // POST: /study
    public ResponseEntity<Map<String, Object>> handleRegisterStudy(@RequestBody Study study, UriComponentsBuilder uriBuilder) {
    	
// 		code 생성 (랜덤 문자열 생성)
        study.setCode(generateStudyCode()); 

// 		서비스 호출하여 스터디 등록
        Study registeredStudy = studyService.addStudy(study);
        
        // 가입 URL 생성
        String signupUrl = uriBuilder
            .path("/study-invite/")
            .path(registeredStudy.getCode())
            .build()
            .toUriString();

        // 응답 데이터 구성
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("study_id", registeredStudy.getStudyId());
        response.put("owner_id", registeredStudy.getOwnerId());
        response.put("name", registeredStudy.getName());
        response.put("description", registeredStudy.getDescription());
        response.put("code", registeredStudy.getCode());
        response.put("signup_url", signupUrl);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//	스터디 정보 상세 조회
    @GetMapping("/{study-id}")  // GET: /study/{study-id}
    public ResponseEntity<Map<String, Object>> getStudyDetail(@PathVariable("study-id") int studyId) {
    	
//    	서비스 호출하여 스터디 정보 조회
    	Study study = studyService.getStudyById(studyId);
    	
    	// 스터디 소유자의 handle 조회
        User owner = userService.getUserById(study.getOwnerId());  // owner의 user_id로 handle 조회

        // 스터디에 속한 멤버 조회 (User 리스트로 반환)
        List<Map<String, Object>> studyMembers = studyService.getUsersByStudyId(studyId);
    	
//    	응답 생성
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("study_id", study.getStudyId());
        response.put("owner_id", study.getOwnerId());
        response.put("owner_handle", owner.getHandle());
        response.put("name", study.getName());
        response.put("description", study.getDescription());
        response.put("code", study.getCode());
        response.put("study_member_cnt", studyMembers.size());
        response.put("study_member", studyMembers);
        
        return ResponseEntity.ok(response);
    }
    // 랜덤한 스터디 code를 생성하는 메서드
    private String generateStudyCode() {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
    
//  코드로 스터디 정보 조회
    @GetMapping("/code/{code}") // GET: /study/code/{code}
    public ResponseEntity<Map<String, Object>> getStudyByCode(@PathVariable String code){
//		응답 객체
		Map<String, Object> response = new LinkedHashMap<>();
		
//		서비스에 정보 요청
		Study study = studyService.getStudyByCode(code);
		String ownerHandle = userService.getUserById(study.getOwnerId()).getHandle();
		
//		응답 객체 생성
		response.put("study_id", study.getStudyId());
		response.put("owner_id", study.getOwnerId());
		response.put("ownder_handle", ownerHandle);
		response.put("name", study.getName());
		response.put("description", study.getDescription());
		response.put("code", code);
		
    	return ResponseEntity.ok(response);
    }
    
//  스터디ID로 스터디 삭제
    @DeleteMapping("/{study-id}") // DELETE: /study/{study-id}
    public ResponseEntity<Void> deleteStudyById(@PathVariable("study-id") int studyId){
//    	서비스에 삭제 요청
    	studyService.deleteStudyById(studyId);
    	return ResponseEntity.noContent().build();
    }
    
//  특정 유저의 스터디 탈퇴
    @DeleteMapping("/user/{study-id}/{user-id}") // DELETE: /study/user/{study-id}/{user-id}
    public ResponseEntity<Void> leaveStudyById(@PathVariable("study-id") int studyId, @PathVariable("user-id") int userId){
//    	서비스에 탈퇴 요청
    	studyService.leaveStudyById(studyId, userId);
    	return ResponseEntity.noContent().build();
    }
    
 // POST: /study/signup
 // 특정 유저의 스터디 가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> handleSignupStudy(@RequestBody Map<String, Object> payload) {
        int userId = (Integer) payload.get("user_id");
        String studyCode = (String) payload.get("study_code");

        Study study = studyService.getStudyByCode(studyCode);
        if (study == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Study not found with this code.");
        }

        int isSignedUp = studyService.insertStudyMember(study.getStudyId(), userId);
        if (isSignedUp == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to signup user to study");
        }

        //응답 객체 생성
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("user_id", userId);
        response.put("study_code", studyCode);
        response.put("message", "Successfully joined the study");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    //GET: /study/user/{user-id}
    //특정 유저가 가입한 모든 스터디 정보 리스트 조회
    @GetMapping("/user/{user-id}")
    public ResponseEntity<Map<String, Object>> getUserStudies(@PathVariable("user-id") int userId){
    	List<Study> userStudies = studyService.getStudiesByUserId(userId);
    	
    	if (userStudies == null || userStudies.isEmpty()) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 사용자 ID로 등록된 스터디가 없습니다. "+userId);
    	}
    	
    	List<Map<String, Object>> studiesInfo = userStudies.stream().map(study -> {
    		Map<String, Object> studyInfo = new LinkedHashMap<>();
    		studyInfo.put("study_id", study.getStudyId());
    		studyInfo.put("name", study.getName());
    		studyInfo.put("description", study.getDescription());
    		studyInfo.put("code", study.getCode());
    		return studyInfo;
    	}).collect(Collectors.toList());
    	
    	Map<String, Object> response = new LinkedHashMap<>();
    	response.put("user_id", userId);
    	response.put("num_studies", userStudies.size());
    	response.put("studies", studiesInfo);
    	
    	return ResponseEntity.ok(response);
    }
    
    
    
}
