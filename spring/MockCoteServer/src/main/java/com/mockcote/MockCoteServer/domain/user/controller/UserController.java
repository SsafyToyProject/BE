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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	
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
