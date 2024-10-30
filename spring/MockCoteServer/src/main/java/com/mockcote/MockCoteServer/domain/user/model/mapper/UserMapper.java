package com.mockcote.MockCoteServer.domain.user.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mockcote.MockCoteServer.domain.user.dto.User;

public interface UserMapper {

//	userId로 유저 정보 요청
	User getUserById(int userId);

//	userId로 회원 삭제 요청
	int deleteUserById(int userId);
	
	/**
	 * user_id 리스트를 대응하는 User 리스트로 변환
	 * @param userIds list
	 * @return User list
	 */
	List<User> getUsersByUserIds(@Param("userIds") List<Integer> userIds);

}
