package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.json.JSONObject;

import model.dto.UserDto;
import service.UserService;

@WebServlet(urlPatterns = { "/user/*" })
public class UserController extends HttpServlet {

    private UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null) {
            if (path.equals("/login")) {
                handleLogin(request, response); // 로그인 처리
            } else if (path.equals("/signup")) {
                handleSignup(request, response); // 회원가입 처리
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Invalid path");
            }
        } else {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Missing path information");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null && path.matches("^/\\d+$")) { // /user/{user_id}
            getUserInfo(request, response); // 회원 정보 조회
        } else {
            sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Invalid path");
        }
    }

    // 로그인 처리 (세션과 쿠키 포함)
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 요청에서 handle과 password 가져오기
        StringBuilder jsonBuffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }
        JSONObject requestBody = new JSONObject(jsonBuffer.toString());
        String handle = requestBody.getString("handle");
        String password = requestBody.getString("password");

        // 로그인 처리
        UserDto user = userService.login(handle, password);
        if (user == null) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid login credentials");
        } else {
            // 세션 생성 및 쿠키 설정
            HttpSession session = request.getSession(true); // 세션 생성 또는 기존 세션 반환
            session.setAttribute("user", user);

            // 세션 ID를 쿠키에 추가
            Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/"); // 전체 애플리케이션에서 사용할 수 있도록 설정
            response.addCookie(sessionCookie);

            JSONObject successResponse = new JSONObject();
            successResponse.put("status", HttpServletResponse.SC_OK);
            successResponse.put("user_id", user.getUser_id());
            successResponse.put("handle", user.getHandle());
            successResponse.put("level", user.getLevel());

            sendSuccessResponse(response, successResponse);
        }
    }

    // 회원가입 처리
    private void handleSignup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 요청에서 회원가입 정보 가져오기
        StringBuilder jsonBuffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }
        JSONObject requestBody = new JSONObject(jsonBuffer.toString());
        String handle = requestBody.getString("handle");
        String password = requestBody.getString("password");
        int level = requestBody.getInt("level");

        // 회원가입 처리
        UserDto newUser = new UserDto(-1, handle, password, level);
        UserDto addedUser = userService.addUser(newUser);

        if (addedUser == null) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to register user");
        } else {
            JSONObject successResponse = new JSONObject();
            successResponse.put("status", HttpServletResponse.SC_CREATED);
            successResponse.put("user_id", addedUser.getUser_id());
            successResponse.put("handle", addedUser.getHandle());
            successResponse.put("level", addedUser.getLevel());

            sendSuccessResponse(response, successResponse);
        }
    }

    // 회원 정보 조회 처리 (세션을 활용)
    private void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기 (있으면)
        if (session == null || session.getAttribute("user") == null) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in");
            return;
        }

        UserDto loggedInUser = (UserDto) session.getAttribute("user");

        JSONObject successResponse = new JSONObject();
        successResponse.put("status", HttpServletResponse.SC_OK);
        successResponse.put("user_id", loggedInUser.getUser_id());
        successResponse.put("handle", loggedInUser.getHandle());
        successResponse.put("level", loggedInUser.getLevel());

        sendSuccessResponse(response, successResponse);
    }

    // 성공 응답 전송 메서드
    private void sendSuccessResponse(HttpServletResponse response, JSONObject successResponse) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(successResponse.toString());
        out.flush();
    }

    // 에러 응답 전송 메서드
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String errorMessage)
            throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject errorResponse = new JSONObject();
        errorResponse.put("status", statusCode);
        errorResponse.put("error", errorMessage);
        PrintWriter out = response.getWriter();
        out.print(errorResponse.toString());
        out.flush();
    }
}
