package com.mockcote.MockCoteServer.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mockcote.MockCoteServer.config.JwtUtil;
import com.mockcote.MockCoteServer.domain.user.dto.AuthenticationRequest;
import com.mockcote.MockCoteServer.domain.user.dto.AuthenticationResponse;
import com.mockcote.MockCoteServer.domain.user.dto.GetUserResponse;
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
	private final JwtUtil jwtUtil;
	
	@Operation(summary = "회원정보조회", description = "user_id, handle(백준아이디), level을 반환해 줍니다.")
//	유저Id로 회원 정보 조회
	@GetMapping("/{user-id}") //GET: /user/{user-id}
	public ResponseEntity<GetUserResponse> getUserById(@PathVariable("user-id") int userId) {
//		서비스에 유저 정보 요청
		User user = userService.getUserById(userId);
		
//		응답 객체 생성
		GetUserResponse response = new GetUserResponse(
				user.getUserId(),
				user.getHandle(),
				user.getLevel()
				);
		return ResponseEntity.ok(response);
	}
	
	@Operation(summary = "회원탈퇴", description = "user_id로 회원 탈퇴합니다.")
//	유저Id로 회원 탈퇴 //DELETE: /user/{user-id}
	@DeleteMapping("/{user-id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable("user-id") int userId){
//		서비스에 유저 탈퇴 요청
		userService.deleteUserById(userId);
		
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "로그인", description = "사용자의 핸들과 비밀번호로 로그인하고 JWT 토큰을 발급합니다.")
	@PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        User user = userService.authenticateUser(request.getHandle(), request.getPassword());
        String token = jwtUtil.generateToken(user.getHandle());
        
        AuthenticationResponse response = new AuthenticationResponse(
                user.getUserId(),
                user.getHandle(),
                user.getLevel(),
                token
        );
        return ResponseEntity.ok(response);
    }
	
	@Operation(summary = "회원가입", description = "새로운 사용자를 등록하고 JWT 토큰을 발급합니다.")
	@PostMapping()
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) {
        User newUser = userService.registerUser(user);
        String token = jwtUtil.generateToken(newUser.getHandle());

        AuthenticationResponse response = new AuthenticationResponse(
                newUser.getUserId(),
                newUser.getHandle(),
                newUser.getLevel(),
                token
        );
        return ResponseEntity.ok(response);
    }
	
}
