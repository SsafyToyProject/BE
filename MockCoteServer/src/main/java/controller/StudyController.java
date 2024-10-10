package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.StudyDto;
import service.StudyService;

@WebServlet(urlPatterns = { "/study/*" })
public class StudyController extends HttpServlet {

    private StudyService studyService = StudyService.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null) {
            if (path.startsWith("/")) {
                // GET: /study/{studyID}
                getStudyDetail(request, response);
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
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null) {
            if (path.equals("/register")) {
                // POST: /study/register
                handleRegisterStudy(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JSONObject errorResponse = new JSONObject();
                errorResponse.put("error", "Invalid path");
                PrintWriter out = response.getWriter();
                out.print(errorResponse.toString());
                out.flush();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Missing path information");
            PrintWriter out = response.getWriter();
            out.print(errorResponse.toString());
            out.flush();
        }
    }

 // 특정 스터디 ID의 상세 정보를 조회하는 메서드
    private void getStudyDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        // 경로 검증
        if (pathParts.length < 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
            return;
        }

        int studyId = -1;
        try {
            studyId = Integer.parseInt(pathParts[1]);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
            return;
        }

        // 서비스 호출하여 스터디 정보 조회
        StudyDto study = studyService.getStudyById(studyId);
        if (study == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 404 Not Found
            return;
        }

        // 스터디에 속한 멤버 조회
        List<Integer> studyMembers = studyService.getUsersByStudyId(studyId);

        // JSON 응답 생성
        JSONObject responseBody = new JSONObject();
        responseBody.put("study_id", study.getStudy_id());
        responseBody.put("owner_id", study.getOwner_id());
        responseBody.put("name", study.getName());
        responseBody.put("description", study.getDescription());
        responseBody.put("code", study.getCode());
        responseBody.put("study_member_cnt", studyMembers.size());

        JSONArray memberArray = new JSONArray();
        for (int memberId : studyMembers) {
            JSONObject memberJson = new JSONObject();
            memberJson.put("user_id", memberId);
            memberArray.put(memberJson);
        }
        responseBody.put("study_member", memberArray);

        // 응답 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(responseBody.toString());
        out.flush();
    }

	

    private void handleRegisterStudy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 요청 파라미터 파싱
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }
        JSONObject requestBody = new JSONObject(jsonBuffer.toString());

        // 요청 값 추출
        int ownerId = requestBody.getInt("owner_id");
        String name = requestBody.getString("name");
        String description = requestBody.getString("description");
        String code = requestBody.getString("code");

        // StudyDto 생성
        StudyDto newStudy = new StudyDto(-1, ownerId, name, description, code);

        // 서비스 호출하여 스터디 등록
        StudyDto registeredStudy = studyService.addStudy(newStudy);

        // 등록 실패 시
        if (registeredStudy == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Failed to register study");
            PrintWriter out = response.getWriter();
            out.print(errorResponse.toString());
            out.flush();
            return;
        }

        // 등록 성공 시 응답
        response.setStatus(HttpServletResponse.SC_CREATED);  // 201 Created
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject responseBody = new JSONObject();
        responseBody.put("study_id", registeredStudy.getStudy_id());
        responseBody.put("owner_id", registeredStudy.getOwner_id());
        responseBody.put("name", registeredStudy.getName());
        responseBody.put("description", registeredStudy.getDescription());
        responseBody.put("code", registeredStudy.getCode());

        PrintWriter out = response.getWriter();
        out.print(responseBody.toString());
        out.flush();
    }
}
