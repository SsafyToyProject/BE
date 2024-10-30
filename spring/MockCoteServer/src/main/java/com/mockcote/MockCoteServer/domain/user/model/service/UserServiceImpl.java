package com.mockcote.MockCoteServer.domain.user.model.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mockcote.MockCoteServer.domain.user.dto.User;
import com.mockcote.MockCoteServer.domain.user.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	
//	userId로 유저 정보 받아오기
	@Override
	public User getUserById(int userId) {
		User user = userMapper.getUserById(userId);
//		해당 유저가 없을 때 예외 처리
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID");
        }
        return user;
	}

//	userId로 회원 탈퇴
	@Transactional
	@Override
	public int deleteUserById(int userId) {
		int result = userMapper.deleteUserById(userId);
//		해당 유저가 없을 때 예외 처리
        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to delete user with ID");
        }
        return result;
	}

}
