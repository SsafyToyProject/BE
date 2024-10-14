package service;

import model.dao.UserDao;
import model.dao.UserDaoImpl;
import model.dto.UserDto;

public class UserService {

    private static UserService instance = new UserService();
    private UserDao userDao = UserDaoImpl.getInstance();

    private UserService() {}

    public static UserService getInstance() {
        return instance;
    }

    // 로그인 처리
    public UserDto login(String handle, String password) {
        UserDto user = userDao.searchUserHandle(handle);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // 회원가입 처리
    public UserDto addUser(UserDto userDto) {
        return userDao.addUser(userDto);
    }

    // 회원 정보 조회 처리
    public UserDto getUserById(int userId) {
        return userDao.searchUserById(userId);
    }
    
    public UserDto getUserByHandle(String handle) {
        return userDao.searchUserHandle(handle);
    }
}
