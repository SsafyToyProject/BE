package com.mockcote.MockCoteServer.domain.user.model.service;

import com.mockcote.MockCoteServer.domain.user.dto.User;

public interface UserService {
	/**
     * 유저ID로 유저 정보 조회
     * @param userId
     * @return user
     */
	User getUserById(int userId);

	/**
     * 유저ID로 회원 탈퇴
     * @param userId
     * @return int
     */
	int deleteUserById(int userId);
	
	/**
	 * 회원가입
	 * @param user
	 * @return
	 */
	User registerUser(User user);
	
	/**
	 * 로그인
	 * @param handle
	 * @param password
	 * @return
	 */
	User authenticateUser(String handle, String password);
	
	/**
	 * user의 JWT 토큰 생성하기
	 * @param user
	 * @return
	 */
	String generateToken(User user);
	
}
