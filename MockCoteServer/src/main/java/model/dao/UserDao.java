package model.dao;


import java.util.List;
import model.dto.UserDto;

/**
 * user를 관리하는 Dao
 */

public interface UserDao {

	/**
	 * 회원 추가하기
	 * @param 추가할 회원 객체
	 * @return 추가된 회원 객체
	 */
	UserDto addUser(UserDto userDto);
	
//	/**
//	 * 회원 아이디 찾기
//	 * @param 회원을 찾기 위한 이름 정보
//	 * @return 입력된 정보의 회원 정보 객체
//	 */ 
//	UserDto searchUserHandle(String handle);
	
	/**
	 * 회원 비밀번호 찾기
	 * @param 회원을 찾기위한 아이디, 이름 정보
	 * @return 입력된 정보의 회원 정보 객체
	 */
	UserDto searchUserPw(String handle);
	
	/**
	 * 회원 정보 바꾸기
	 * @param 수정할 회원 객체
	 * @return 수정한 회원 객체
	 */
	UserDto updateUser(UserDto userDto);
	
	/**
	 * 회원 탈퇴하기
	 * @param 탈퇴할 회원 객체
	 * @return 성공한 탈퇴 회원 수
	 */
	UserDto deleteUser(UserDto userDto);
	
}
