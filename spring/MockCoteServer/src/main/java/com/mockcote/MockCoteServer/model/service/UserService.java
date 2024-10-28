package com.mockcote.MockCoteServer.model.service;

import com.mockcote.MockCoteServer.dto.User;

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
}
