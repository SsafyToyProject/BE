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
import model.dto.UserDto;
import service.StudyService;

@WebServlet(urlPatterns = { "/study/*" })
public class StudyController extends HttpServlet {

    private StudyService studyService = StudyService.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null) {
        	 if (path.startsWith("/user/")) {
                 // GET: /study/user/{user_id}
                 getUserStudyList(request, response);
        	 }
        	 else if (path.startsWith("/")) {
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
            if (path.equals("/register")) { // 스터디 등록
                handleRegisterStudy(request, response);
            } else if (path.equals("/signup")) { // 스터디 코드로 가입 처리
                handleSignupStudy(request, response);  // 수정된 가입 처리 메서드
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

    
    // 사용자가 가입한 스터디 ID 목록을 조회하는 메서드
    private void getUserStudyList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        // 경로 검증
        if (pathParts.length < 3) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
            return;
        }

        int userId = -1;
        try {
            userId = Integer.parseInt(pathParts[2]);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
            return;
        }

        // 서비스 호출하여 사용자가 가입한 스터디 목록 조회
        List<Integer> userStudyList = studyService.getStudiesByUserId(userId);

        if (userStudyList == null || userStudyList.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 404 Not Found
            return;
        }

        // JSON 응답 생성
        JSONObject responseBody = new JSONObject();
        responseBody.put("user_id", userId);
        responseBody.put("num_studies", userStudyList.size());

        JSONArray studyArray = new JSONArray();
        for (int studyId : userStudyList) {
            JSONObject studyJson = new JSONObject();
            studyJson.put("study_id", studyId);
            studyArray.put(studyJson);
        }
        responseBody.put("studies", studyArray);

        // 응답 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(responseBody.toString());
        out.flush();
    }
    

 // 스터디 코드로 가입 처리하는 메서드
    private void handleSignupStudy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 요청 본문에서 user_id와 study_code 가져오기
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }
        JSONObject requestBody = new JSONObject(jsonBuffer.toString());

        int userId = requestBody.getInt("user_id");
        String studyCode = requestBody.getString("study_code");

        // 스터디 코드로 study_id 조회
        StudyDto study = studyService.getStudyByCode(studyCode);
        if (study == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Study not found with this code.");
            PrintWriter out = response.getWriter();
            out.print(errorResponse.toString());
            out.flush();
            return;
        }

        // 서비스 호출하여 스터디 가입 처리
        boolean isSignedUp = studyService.insertStudyMember(study.getStudy_id(), userId);
        if (isSignedUp) {
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            JSONObject responseBody = new JSONObject();
            responseBody.put("user_id", userId);
            responseBody.put("study_code", studyCode);  // 요청에서 받은 study_code 반환
            responseBody.put("message", "Successfully joined the study");
            PrintWriter out = response.getWriter();
            out.print(responseBody.toString());
            out.flush();
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Failed to signup user to study");
            PrintWriter out = response.getWriter();
            out.print(errorResponse.toString());
            out.flush();
        }
    }


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

        // 스터디에 속한 멤버 조회 (UserDto 리스트로 반환)
        List<UserDto> studyMembers = studyService.getUsersByStudyId(studyId);

        // JSON 응답 생성
        JSONObject responseBody = new JSONObject();
        responseBody.put("study_id", study.getStudy_id());
        responseBody.put("owner_id", study.getOwner_id());
        responseBody.put("name", study.getName());
        responseBody.put("description", study.getDescription());
        responseBody.put("code", study.getCode());
        responseBody.put("study_member_cnt", studyMembers.size());

        JSONArray memberArray = new JSONArray();
        for (UserDto member : studyMembers) {
            JSONObject memberJson = new JSONObject();
            memberJson.put("user_id", member.getUser_id());
            memberJson.put("handle", member.getHandle());
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

        // code 생성 (랜덤 문자열 생성)
        String code = generateStudyCode();

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

        // 등록 성공 시 응답 (스터디 가입 URL 포함)
        response.setStatus(HttpServletResponse.SC_CREATED);  // 201 Created
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject responseBody = new JSONObject();
        responseBody.put("study_id", registeredStudy.getStudy_id());
        responseBody.put("owner_id", registeredStudy.getOwner_id());
        responseBody.put("name", registeredStudy.getName());
        responseBody.put("description", registeredStudy.getDescription());
        responseBody.put("code", registeredStudy.getCode());

        // 가입 URL을 응답에 포함
        String signupUrl = request.getRequestURL().toString().replace("/register", "/signup/" + registeredStudy.getCode());
        responseBody.put("signup_url", signupUrl);

        PrintWriter out = response.getWriter();
        out.print(responseBody.toString());
        out.flush();
    }

    // 랜덤한 스터디 code를 생성하는 메서드
    private String generateStudyCode() {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }

}
