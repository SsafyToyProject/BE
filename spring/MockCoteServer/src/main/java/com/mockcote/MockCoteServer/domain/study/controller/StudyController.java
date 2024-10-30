package com.mockcote.MockCoteServer.domain.study.controller;

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

import com.mockcote.MockCoteServer.domain.study.dto.CodeStudyResponse;
import com.mockcote.MockCoteServer.domain.study.dto.CreateStudyResponse;
import com.mockcote.MockCoteServer.domain.study.dto.DetailStudyResponse;
import com.mockcote.MockCoteServer.domain.study.dto.Study;
import com.mockcote.MockCoteServer.domain.study.model.service.StudyService;
import com.mockcote.MockCoteServer.domain.user.dto.User;
import com.mockcote.MockCoteServer.domain.user.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
@Tag(name = "Study Controller", description = "스터디 생성, 스터디 조회, 스터디 가입과 탈퇴 등 을 제공하는 클래스")
public class StudyController {
	
    private final StudyService studyService;
    private final UserService userService;    
	
    @Operation(summary = "스터디 생성", description = "스터디를 생성합니다.")
//	스터디 생성
    @PostMapping // POST: /study
    public ResponseEntity<CreateStudyResponse> handleRegisterStudy(@RequestBody CreateStudyResponse study, UriComponentsBuilder uriBuilder) {
    	
        // 가입 URL 생성
        String signupUrl = uriBuilder
        		.path("/study-invite/")
        		.path(study.getCode())
        		.build()
        		.toUriString();
        study.setSignupUrl(signupUrl);
        
// 		서비스 호출하여 스터디 등록
        CreateStudyResponse response = studyService.addStudy(study);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "스터디 정보 상세 조회", description = "study_id로 스터디의 정보를 조회합니다.")
//	스터디 정보 상세 조회
    @GetMapping("/{study-id}")  // GET: /study/{study-id}
    public ResponseEntity<DetailStudyResponse> getStudyDetail(@PathVariable("study-id") int studyId) {
    	
//    	서비스 호출하여 스터디 정보 조회
    	Study study = studyService.getStudyById(studyId);
    	
    	// 스터디 소유자의 handle 조회
        User owner = userService.getUserById(study.getOwnerId());  // owner의 user_id로 handle 조회

//    	응답 생성
        DetailStudyResponse response = new DetailStudyResponse(
        		study.getStudyId(),
        		study.getOwnerId(),
        		owner.getHandle(),
        		study.getName(),
        		study.getDescription(),
        		study.getCode(),
        		study.getStudyMembers().size(),
        		study.getStudyMembers()
        		);
        
        return ResponseEntity.ok(response);
    }	
    
    @Operation(summary = "초대코드로 스터디 조회", description = "code로 스터디 정보를 조회합니다.")
//  코드로 스터디 정보 조회
    @GetMapping("/code/{code}") // GET: /study/code/{code}
    public ResponseEntity<CodeStudyResponse> getStudyByCode(@PathVariable String code){
		
//		서비스에 정보 요청
		Study study = studyService.getStudyByCode(code);
		String ownerHandle = userService.getUserById(study.getOwnerId()).getHandle();
		
//		응답 객체 생성
		CodeStudyResponse response = new CodeStudyResponse(
				study.getStudyId(),
				study.getOwnerId(),
				ownerHandle,
				study.getName(),
				study.getDescription(),
				code
				);
		
    	return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "스터디 삭제", description = "study_id로 스터디를 삭제합니다.")
//  스터디ID로 스터디 삭제
    @DeleteMapping("/{study-id}") // DELETE: /study/{study-id}
    public ResponseEntity<Void> deleteStudyById(@PathVariable("study-id") int studyId){
//    	서비스에 삭제 요청
    	studyService.deleteStudyById(studyId);
    	return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "특정 유저의 스터디 탈퇴", description = "study_id에 해당하는 스터디를 user_id를 가진 회원이 탈퇴합니다.")
//  특정 유저의 스터디 탈퇴
    @DeleteMapping("/user/{study-id}/{user-id}") // DELETE: /study/user/{study-id}/{user-id}
    public ResponseEntity<Void> leaveStudyById(@PathVariable("study-id") int studyId, @PathVariable("user-id") int userId){
//    	서비스에 탈퇴 요청
    	studyService.leaveStudyById(studyId, userId);
    	
//    	탈퇴한 유저가 방장이라면?
    	
    	return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "특정 유저의 스터디 가입", description = "해당 스터디에 user가 가입합니다.")
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
    
    @Operation(summary = "특정 유저가 가입한 스터디 정보 조회", description = "유저가 가입한 스터디 정보를 조회합니다.")
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
