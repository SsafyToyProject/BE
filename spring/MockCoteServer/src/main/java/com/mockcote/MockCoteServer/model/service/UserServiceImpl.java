package com.mockcote.MockCoteServer.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mockcote.MockCoteServer.dto.User;
import com.mockcote.MockCoteServer.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	
//	userId로 유저 정보 받아오기
	@Override
	public User getUserById(int userId) {
		return userMapper.getUserById(userId);
	}

//	userId로 회원 탈퇴
	@Transactional
	@Override
	public int deleteUserById(int userId) {
		return userMapper.deleteUserById(userId);
	}

}
