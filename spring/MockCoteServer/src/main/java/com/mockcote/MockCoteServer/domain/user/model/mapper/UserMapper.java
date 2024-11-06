package com.mockcote.MockCoteServer.domain.user.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mockcote.MockCoteServer.domain.user.dto.User;

@Mapper
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
	
	/**
	 * user 정보 삽입
	 * @param user
	 * @return
	 */
	int insertUser(User user);
	
	/**
	 * handle(백준 ID)로 유저 정보 가져오기
	 * @param handle
	 * @return
	 */
	User getUserByHandle(String handle);
	
	/**
	 * 리프레시 토큰 저장
	 * @param userId
	 * @param refreshToken
	 */
    void insertRefreshToken(@Param("userId") int userId, @Param("refreshToken") String refreshToken);

    /**
     * refresh토큰 가져오기
     * @param userId
     * @return
     */
    String getRefreshTokenByUserId(@Param("userId") int userId);

    /**
     * 리프레시 토큰 삭제
     * @param userId
     */
    void deleteRefreshTokenByUserId(@Param("userId") int userId);
}
