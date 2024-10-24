package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.StudyDto;
import model.dto.UserDto;
import util.DBUtil;

public class StudyDaoImpl implements StudyDao {

	private static StudyDao instance = new StudyDaoImpl();

	private StudyDaoImpl() {}

	public static StudyDao getInstance() {
		return instance;
	}

	@Override
	public StudyDto addStudy(StudyDto studyDto) {
	    String sql = "INSERT INTO studies (owner_id, name, description, code) VALUES (?, ?, ?, ?)";
	    String sqlStudyMember = "INSERT INTO study_members (study_id, user_id) VALUES (?, ?)";
	    Connection conn = null;
	    PreparedStatement ps = null;
	    PreparedStatement psStudyMember = null;
	    ResultSet rs = null;

	    try {
	        conn = DBUtil.getConnection();
	        conn.setAutoCommit(false);  // 트랜잭션 시작

	        // 1. studies 테이블에 스터디 등록
	        ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        ps.setInt(1, studyDto.getOwner_id());
	        ps.setString(2, studyDto.getName());
	        ps.setString(3, studyDto.getDescription());
	        ps.setString(4, studyDto.getCode());

	        int affectedRows = ps.executeUpdate();

	        if (affectedRows > 0) {
	            rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                int studyId = rs.getInt(1);
	                studyDto.setStudy_id(studyId); // 자동 생성된 study_id를 DTO에 설정

	                // 2. study_members 테이블에 owner_id를 추가 (study의 owner는 자동으로 study_members에 등록됨)
	                psStudyMember = conn.prepareStatement(sqlStudyMember);
	                psStudyMember.setInt(1, studyId);
	                psStudyMember.setInt(2, studyDto.getOwner_id());
	                psStudyMember.executeUpdate();
	            }
	        }

	        conn.commit();  // 트랜잭션 커밋
	    } catch (SQLException e) {
	        e.printStackTrace();
	        if (conn != null) {
	            try {
	                conn.rollback();  // 오류 발생 시 롤백
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    } finally {
	        DBUtil.close(rs, ps, conn);
	        DBUtil.close(psStudyMember); // 두 번째 PreparedStatement 닫기
	    }

	    return studyDto;
	}


	// 스터디 검색
	@Override
	public StudyDto searchStudy(String name) {
		String sql = "SELECT * FROM studies WHERE name = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StudyDto study = null;

		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();

			if (rs.next()) {
				study = new StudyDto(rs.getInt("study_id"), rs.getInt("owner_id"), rs.getString("name"), rs.getString("description"),rs.getString("code"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs, ps, conn);
		}

		return study;
	}

	// 스터디 수정
	@Override
	public StudyDto updateStudy(StudyDto studyDto) {
		String sql = "UPDATE studies SET name = ?, description = ?, code = ? WHERE study_id = ?";
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, studyDto.getName());
			ps.setString(2, studyDto.getDescription());
			ps.setString(3, studyDto.getCode());
			ps.setInt(4, studyDto.getStudy_id());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, conn);
		}

		return studyDto;
	}

	// 스터디 삭제
	@Override
	public StudyDto deleteStudy(String name) {
		String sql = "DELETE FROM studies WHERE name = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		StudyDto deletedStudy = searchStudy(name); // 삭제하기 전에 해당 스터디 정보를 검색

		if (deletedStudy == null) {
			return null; // 스터디가 존재하지 않으면 null 반환
		}

		try {
			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, conn);
		}

		return deletedStudy;
	}
	
	//스터디에 스터디원 추가
	@Override
	public boolean insertStudyMember(int studyId, int userId) {
	    String sql = "INSERT INTO study_members (study_id, user_id) VALUES (?, ?)";
	    Connection conn = null;
	    PreparedStatement ps = null;
	    boolean res = false;

	    try {
	        conn = DBUtil.getConnection();
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, studyId);
	        ps.setInt(2, userId);

	        int result = ps.executeUpdate();
	        if (result > 0) res = true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.close(ps, conn);
	    }

	    return res;
	}
	
	@Override
	public List<UserDto> getUsersByStudyId(int studyId) {
	    String sql = "SELECT u.user_id, u.handle FROM users u "
	               + "JOIN study_members sm ON u.user_id = sm.user_id "
	               + "WHERE sm.study_id = ?";
	    
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<UserDto> userList = new ArrayList<>();

	    try {
	        conn = DBUtil.getConnection();  // DB 연결
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, studyId);  // studyId를 SQL 쿼리에 바인딩
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            int userId = rs.getInt("user_id");  // user_id 가져오기
	            String handle = rs.getString("handle");  // handle 가져오기
	            UserDto user = new UserDto(userId, handle,null,0);  // UserDto 객체 생성
	            userList.add(user);  // 리스트에 추가
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.close(rs, ps, conn);  // 리소스 해제
	    }

	    return userList;  // study_id에 가입된 모든 사용자의 정보(UserDto) 리스트를 반환
	}

	
	// 스터디 상세 조회
    @Override
    public StudyDto getStudyById(int studyId) {
        String sql = "SELECT * FROM studies WHERE study_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StudyDto study = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studyId);
            rs = ps.executeQuery();

            if (rs.next()) {
                study = new StudyDto(
                    rs.getInt("study_id"),
                    rs.getInt("owner_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }

        return study;
    }
    
 // 사용자가 가입한 스터디 목록을 조회
    @Override
    public List<Integer> getStudiesByUserId(int userId) {
        String sql = "SELECT study_id FROM study_members WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Integer> studyIds = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                studyIds.add(rs.getInt("study_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }

        return studyIds;
    }
    
    /**
     * 스터디 코드로 스터디 조회
     *
     * @param code 스터디 코드
     * @return StudyDto 객체
     */
    @Override
    public StudyDto getStudyByCode(String code) {
        String sql = "SELECT * FROM studies WHERE code = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StudyDto study = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                study = new StudyDto(
                    rs.getInt("study_id"),
                    rs.getInt("owner_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }

        return study;
    }

}
