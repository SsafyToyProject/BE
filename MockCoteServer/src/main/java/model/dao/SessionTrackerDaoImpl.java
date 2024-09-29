package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.SessionTrackerDao;
import model.dto.SessionTrackerDto;
import util.DBUtil;

public class SessionTrackerDaoImpl implements SessionTrackerDao {

    @Override
    public List<SessionTrackerDto> getAllSessionTrackers() throws SQLException {
        List<SessionTrackerDto> trackers = new ArrayList<>();
        String sql = "SELECT session_id, user_id, problem_id, solved_at, performance, language, code_link, description FROM session_tracker";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                trackers.add(new SessionTrackerDto(
                    rs.getInt("session_id"),
                    rs.getInt("user_id"),
                    rs.getInt("problem_id"),
                    rs.getString("solved_at"),
                    rs.getInt("performance"),
                    rs.getString("language"),
                    rs.getString("code_link"),
                    rs.getString("description")
                ));
            }
        }
        return trackers;
    }

    @Override
    public SessionTrackerDto getSessionTrackerById(int sessionId, int userId) throws SQLException {
        String sql = "SELECT session_id, user_id, problem_id, solved_at, performance, language, code_link, description FROM session_tracker WHERE session_id = ? AND user_id = ?";
        SessionTrackerDto tracker = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tracker = new SessionTrackerDto(
                        rs.getInt("session_id"),
                        rs.getInt("user_id"),
                        rs.getInt("problem_id"),
                        rs.getString("solved_at"),
                        rs.getInt("performance"),
                        rs.getString("language"),
                        rs.getString("code_link"),
                        rs.getString("description")
                    );
                }
            }
        }
        return tracker;
    }

    @Override
    public boolean insertSessionTracker(SessionTrackerDto tracker) throws SQLException {
        String sql = "INSERT INTO session_tracker (session_id, user_id, problem_id, solved_at, performance, language, code_link, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tracker.getSession_id());
            ps.setInt(2, tracker.getUser_id());
            ps.setInt(3, tracker.getProblem_id());
            ps.setString(4, tracker.getSolved_at());
            ps.setInt(5, tracker.getPerformance());
            ps.setString(6, tracker.getLanguage());
            ps.setString(7, tracker.getCode_link());
            ps.setString(8, tracker.getDescription());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateSessionTracker(SessionTrackerDto tracker) throws SQLException {
        String sql = "UPDATE session_tracker SET problem_id = ?, solved_at = ?, performance = ?, language = ?, code_link = ?, description = ? WHERE session_id = ? AND user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tracker.getProblem_id());
            ps.setString(2, tracker.getSolved_at());
            ps.setInt(3, tracker.getPerformance());
            ps.setString(4, tracker.getLanguage());
            ps.setString(5, tracker.getCode_link());
            ps.setString(6, tracker.getDescription());
            ps.setInt(7, tracker.getSession_id());
            ps.setInt(8, tracker.getUser_id());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteSessionTracker(int sessionId, int userId) throws SQLException {
        String sql = "DELETE FROM session_tracker WHERE session_id = ? AND user_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }
}
