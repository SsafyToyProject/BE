package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.dto.ProblemDto;
import model.dto.QueryDto;
import util.DBUtil;

public class QueryDaoImpl implements QueryDao {

	@Override
	public int insertQuery(QueryDto query) {
		int ret = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql1 = "insert into queries(title,query_str,num_problems) values(?,?,?)";
		String sql2 = "insert into candidates(query_id,problem_id) values(?,?)";
		try {
			conn = DBUtil.getConnection();
			
			//insert into queries
			pstmt = conn.prepareStatement(sql1,PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, query.getTitle());
			pstmt.setString(2, query.getQuery_str());
			pstmt.setInt(3, query.getNum_problems());
			ret += pstmt.executeUpdate();
			DBUtil.close(pstmt);
			
			//get generated_key
			int query_id = -1;
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) query_id = rs.getInt(1);
			
			//insert into candidates
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, query_id);
			for(ProblemDto problem : query.getCandidates()) {
				pstmt.setInt(2, problem.getProblem_id());
				ret += pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs,pstmt,conn);
		}
		return ret;
	}

	@Override
	public List<QueryDto> searchAll() {
		List<QueryDto> ret = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		String sql1 = "select * from queries";
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		String sql2 = "select * from candidates where query_id=?";
		
		try {
			conn = DBUtil.getConnection();
			pstmt1 = conn.prepareStatement(sql1);
			rs1 = pstmt1.executeQuery();
			while(rs1.next()) {
				QueryDto query = new QueryDto();
				query.setQuery_id(rs1.getInt("query_id"));
				query.setQuery_str(rs1.getString("query_str"));
				query.setTitle(rs1.getString("title"));
				query.setNum_problems(rs1.getInt("num_problems"));
				
				List<ProblemDto> cands = new ArrayList<>();
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, query.getQuery_id());
				rs2 = pstmt2.executeQuery();
				while(rs2.next()) {
					cands.add(new ProblemDto(rs2.getInt("problem_id"), rs2.getInt("difficulty"), rs2.getString("title")));
				}
				
				query.setCandidates(cands);
				ret.add(query);
				DBUtil.close(rs2,pstmt2);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs2,pstmt2,rs1,pstmt1,conn);
		}
		return ret;
	}

	@Override
	public QueryDto searchById(int query_id) {
	    QueryDto query = null;
	    Connection conn = null;
	    PreparedStatement pstmt1 = null;
	    ResultSet rs1 = null;
	    String sql1 = "select * from queries where query_id=?";
	    PreparedStatement pstmt2 = null;
	    ResultSet rs2 = null;
	    String sql2 = "select * from candidates where query_id=?";
	    
	    try {
	        conn = DBUtil.getConnection();
	        pstmt1 = conn.prepareStatement(sql1);
	        pstmt1.setInt(1, query_id);
	        rs1 = pstmt1.executeQuery();
	        
	        if (rs1.next()) {
	            query = new QueryDto();
	            query.setQuery_id(rs1.getInt("query_id"));
	            query.setQuery_str(rs1.getString("query_str"));
	            query.setTitle(rs1.getString("title"));
	            query.setNum_problems(rs1.getInt("num_problems"));
	            
	            List<ProblemDto> cands = new ArrayList<>();
	            pstmt2 = conn.prepareStatement(sql2);
	            pstmt2.setInt(1, query.getQuery_id());
	            rs2 = pstmt2.executeQuery();
	            while (rs2.next()) {
	                cands.add(new ProblemDto(rs2.getInt("problem_id"), rs2.getInt("difficulty"), rs2.getString("title")));
	            }
	            
	            query.setCandidates(cands);
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.close(rs2,pstmt2,rs1,pstmt1,conn);
	    }
	    return query;
	}
}
