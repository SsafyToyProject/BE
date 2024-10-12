package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dto.UserDto;
import util.DBUtil;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        return instance;
    }

    // 회원가입
    @Override
    public UserDto addUser(UserDto userDto) {
        String sql = "INSERT INTO users (handle, password, level) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserDto resultUser = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, userDto.getHandle());
            ps.setString(2, userDto.getPassword());
            ps.setInt(3, userDto.getLevel());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    userDto.setUser_id(rs.getInt(1)); // 자동 생성된 user_id 설정
                    resultUser = userDto; // 반환할 회원 객체 설정
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return resultUser;
    }

    // handle로 유저 찾기
    @Override
    public UserDto searchUserHandle(String handle) {
        String sql = "SELECT * FROM users WHERE handle = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserDto user = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, handle);

            rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserDto(
                    rs.getInt("user_id"),
                    rs.getString("handle"),
                    rs.getString("password"),
                    rs.getInt("level")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return user;
    }

    // 비밀번호 찾기
    @Override
    public UserDto searchUserPw(String handle) {
        String sql = "SELECT * FROM users WHERE handle = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserDto user = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, handle);

            rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserDto(
                    rs.getInt("user_id"),
                    rs.getString("handle"),
                    rs.getString("password"),
                    rs.getInt("level")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return user;
    }

    // 회원 정보 수정
    @Override
    public UserDto updateUser(UserDto userDto) {
        String sql = "UPDATE users SET handle = ?, password = ?, level = ? WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        UserDto updatedUser = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userDto.getHandle());
            ps.setString(2, userDto.getPassword());
            ps.setInt(3, userDto.getLevel());
            ps.setInt(4, userDto.getUser_id());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                updatedUser = userDto; // 수정된 회원 객체 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return updatedUser;
    }

    // 회원 탈퇴
    @Override
    public UserDto deleteUser(UserDto userDto) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        UserDto deletedUser = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userDto.getUser_id());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                deletedUser = userDto; // 삭제된 회원 객체 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return deletedUser;
    }
}
