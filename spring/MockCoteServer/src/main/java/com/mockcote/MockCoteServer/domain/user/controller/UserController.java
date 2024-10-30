package com.mockcote.MockCoteServer.domain.user.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mockcote.MockCoteServer.domain.user.dto.User;
import com.mockcote.MockCoteServer.domain.user.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Controller", description = "로그인, 회원정보조회, 회원탈퇴, 로그아웃 등 을 제공하는 클래스")
public class UserController {
	private final UserService userService;
	
	@Operation(summary = "회원정보조회", description = "user_id, handle(백준아이디), level을 반환해 줍니다.")
//	유저Id로 회원 정보 조회
	@GetMapping("/{user_id}") //GET: /user/{user_id}
	public ResponseEntity<Map<String, Object>> getUserById(@PathVariable int user_id) {
//		응답객체
		Map<String, Object> response = new LinkedHashMap<>();
		
//		서비스에 유저 정보 요청
		User user = userService.getUserById(user_id);
		
//		응답객체 생성
		response.put("user_id", user.getUserId());
		response.put("handle", user.getHandle());
		response.put("level", user.getLevel());
		
		return ResponseEntity.ok(response);
	}
	
//	유저Id로 회원 탈퇴 //DELETE: /user/{user_id}
	@DeleteMapping("/{user_id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable int user_id){
//		서비스에 유저 탈퇴 요청
		userService.deleteUserById(user_id);
		return ResponseEntity.noContent().build();
	}
	
}
