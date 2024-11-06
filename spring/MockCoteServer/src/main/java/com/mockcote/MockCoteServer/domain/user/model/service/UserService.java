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
	 * 액세스 토큰 생성
	 * @param user
	 * @return
	 */
	String generateAccessToken(User user);
	
	/**
	 * 리프레시 토큰 생성
	 * @param user
	 * @return
	 */
    String generateRefreshToken(User user);
    
    /**
     * 리프레시 토큰 저장
     * @param userId
     * @param refreshToken
     */
    void saveRefreshToken(int userId, String refreshToken);
    
    /**
     * 액세스 토큰 재발급
     * @param refreshToken
     * @return
     */
    String refreshAccessToken(String refreshToken);
    
    /**
     * 리프레시 토큰 삭제 
     * @param userId
     */
    void deleteRefreshTokenByUserId(int userId);
	
}
