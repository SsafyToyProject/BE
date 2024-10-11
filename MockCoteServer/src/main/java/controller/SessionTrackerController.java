package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.SessionTrackerDto;
import service.SessionTrackerService;

@WebServlet(urlPatterns = { "/tracker/*" })  
public class SessionTrackerController extends HttpServlet {

    private SessionTrackerService sessionTrackerService = SessionTrackerService.getInstance();

 // 기본 GET 요청 처리
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null) {
            if (path.startsWith("/info/")) {
                // GET: /tracker/info/{session_id}/{user_id}/{problem_id}
                getTrackerInfo(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 404 Not Found
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JSONObject errorResponse = new JSONObject();
                errorResponse.put("error", "Invalid path");
                PrintWriter out = response.getWriter();
                out.print(errorResponse.toString());
                out.flush();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Missing path information");
            PrintWriter out = response.getWriter();
            out.print(errorResponse.toString());
            out.flush();
        }
    }

    // POST 요청 처리
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

 // 특정 session_id, user_id, problem_id에 대한 트래커 정보를 가져오는 메서드
    private void getTrackerInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length < 5) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int sessionId = -1;
        int userId = -1;
        int problemId = -1;

        try {
            sessionId = Integer.parseInt(pathParts[2]);
            userId = Integer.parseInt(pathParts[3]);
            problemId = Integer.parseInt(pathParts[4]);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 트래커 정보 조회
        List<SessionTrackerDto> trackers = sessionTrackerService.getSessionTrackers(sessionId, userId, problemId);
        if (trackers.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // JSON 응답 생성
        JSONArray trackerArray = new JSONArray();
        for (SessionTrackerDto tracker : trackers) {
            JSONObject trackerJson = new JSONObject();
            trackerJson.put("solved_at", tracker.getSolved_at().toString());
            trackerJson.put("performance", tracker.getPerformance());
            trackerJson.put("language", tracker.getLanguage());
            trackerJson.put("code_link", tracker.getCode_link());
            trackerJson.put("description", tracker.getDescription());

            trackerArray.put(trackerJson);
        }

        // 응답 본문 작성
        JSONObject responseBody = new JSONObject();
        responseBody.put("num_elements", trackers.size());
        responseBody.put("trackers", trackerArray);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(responseBody.toString());
        out.flush();
    }

}
