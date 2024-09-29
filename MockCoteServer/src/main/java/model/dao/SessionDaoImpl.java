package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.SessionDao;
import model.dto.SessionDto;
import util.DBUtil;

public class SessionDaoImpl implements SessionDao {

    // 모든 세션 가져오기
    @Override
    public List<SessionDto> getAllSessions() throws SQLException {
        List<SessionDto> sessions = new ArrayList<>();
        String sql = "SELECT session_id, study_id, start_at, end_at FROM session";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sessions.add(new SessionDto(
                    rs.getInt("session_id"),
                    rs.getInt("study_id"),
                    rs.getString("start_at"),
                    rs.getString("end_at")
                ));
            }
        }
        return sessions;
    }

    // 특정 ID의 세션 가져오기
    @Override
    public SessionDto getSessionById(int sessionId) throws SQLException {
        String sql = "SELECT session_id, study_id, start_at, end_at FROM session WHERE session_id = ?";
        SessionDto session = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    session = new SessionDto(
                        rs.getInt("session_id"),
                        rs.getInt("study_id"),
                        rs.getString("start_at"),
                        rs.getString("end_at")
                    );
                }
            }
        }
        return session;
    }

    // 세션 삽입
    @Override
    public boolean insertSession(SessionDto session) throws SQLException {
        String sql = "INSERT INTO session (session_id, study_id, start_at, end_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, session.getSession_id());
            ps.setInt(2, session.getStudy_id());
            ps.setString(3, session.getStart_at());
            ps.setString(4, session.getEnd_at());

            return ps.executeUpdate() > 0;
        }
    }

    // 세션 업데이트
    @Override
    public boolean updateSession(SessionDto session) throws SQLException {
        String sql = "UPDATE session SET study_id = ?, start_at = ?, end_at = ? WHERE session_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, session.getStudy_id());
            ps.setString(2, session.getStart_at());
            ps.setString(3, session.getEnd_at());
            ps.setInt(4, session.getSession_id());

            return ps.executeUpdate() > 0;
        }
    }

    // 세션 삭제
    @Override
    public boolean deleteSession(int sessionId) throws SQLException {
        String sql = "DELETE FROM session WHERE session_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            return ps.executeUpdate() > 0;
        }
    }
}
