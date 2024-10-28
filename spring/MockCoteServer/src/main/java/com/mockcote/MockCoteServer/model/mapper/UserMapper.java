package com.mockcote.MockCoteServer.model.mapper;

import com.mockcote.MockCoteServer.dto.User;

public interface UserMapper {

//	userId로 유저 정보 요청
	User getUserById(int userId);

//	userId로 회원 삭제 요청
	int deleteUserById(int userId);

}
