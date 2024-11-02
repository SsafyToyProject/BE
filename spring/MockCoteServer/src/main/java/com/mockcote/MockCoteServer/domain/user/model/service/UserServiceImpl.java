package com.mockcote.MockCoteServer.domain.user.model.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mockcote.MockCoteServer.config.JwtUtil;
import com.mockcote.MockCoteServer.domain.user.dto.User;
import com.mockcote.MockCoteServer.domain.user.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	
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
//		해당 유저가 방장인 스터디에 대한 처리 작성할 예정 (해당 스터디의 멤버가 남아있다면 방장 위임하고 탈퇴)
		
		int result = userMapper.deleteUserById(userId);
//		해당 유저가 없을 때 예외 처리
        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to delete user with ID");
        }
        return result;
	}

	@Override
	@Transactional
    public User registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);  // insertUser 메서드를 UserMapper에 추가해야 함
        return user;
    }

    @Override
    public User authenticateUser(String handle, String password) {
        User user = userMapper.getUserByHandle(handle);
        if (user == null || !user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return user;
    }
    
    @Override
    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getHandle());
    }

}
