package com.mockcote.MockCoteServer.model.service;

import com.mockcote.MockCoteServer.dto.User;

public interface UserService {
//	UserId로 유저 정보 받아오기
	User getUserById(int userId);

//	회원 탈퇴
	int deleteUserById(int userId);
}
