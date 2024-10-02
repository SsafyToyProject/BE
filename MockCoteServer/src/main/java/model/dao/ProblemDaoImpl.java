package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import model.dto.ProblemDto;
import util.DBUtil;

public class ProblemDaoImpl implements ProblemDao {

	@Override
	public int insertProblems(List<ProblemDto> problemList) {
		int ret = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into problems(problem_id,difficulty,title) values(?,?,?) "
				+ "ON DUPLICATE KEY UPDATE difficulty = values(difficulty), title = values(title)";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			for(ProblemDto problem : problemList) {
				pstmt.setInt(1, problem.getProblem_id());
				pstmt.setInt(2, problem.getDifficulty());
				pstmt.setString(3, problem.getTitle());
				ret+= pstmt.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt,conn);
		}
		return ret;
	}

	@Override
	public int updateProblem(ProblemDto problem) {
		int ret = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update problems set difficulty=?, title=? where problem_id=?";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, problem.getProblem_id());
			pstmt.setInt(2, problem.getDifficulty());
			pstmt.setString(3, problem.getTitle());
			ret = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt,conn);
		}
		return ret;
	}

	@Override
	public ProblemDto searchById(int problem_id) {
		ProblemDto ret = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from problems where problem_id=?";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, problem_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ret = new ProblemDto(rs.getInt("problem_id"),rs.getInt("difficulty"),rs.getString("title"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt,conn);
		}
		return ret;
	}

}
