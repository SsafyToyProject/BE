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
import model.dto.SessionDto;
import service.SessionService;

@WebServlet(urlPatterns = { "/session/*" })
public class SessionController extends HttpServlet {

	private SessionService sessionService = SessionService.getInstance();

	// 기본 GET 요청 처리
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path != null) {
			if (path.startsWith("/study/")) {
				// GET: /session/study/{studyID}
				getSessionsByStudyId(request, response);
			} else if (path.matches("/\\d+")) {
				getSessionDetailsById(request, response); // 추가된 세션 상세 조회
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				JSONObject errorResponse = new JSONObject();
				errorResponse.put("error", "Invalid path");
				PrintWriter out = response.getWriter();
				out.print(errorResponse.toString());
				out.flush();
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			JSONObject errorResponse = new JSONObject();
			errorResponse.put("error", "Missing path information");
			PrintWriter out = response.getWriter();
			out.print(errorResponse.toString());
			out.flush();
		}
	}

	// SessionController에 추가된 세션 상세 정보 조회 메서드
	private void getSessionDetailsById(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    // sessionID 추출
	    String pathInfo = request.getPathInfo();
	    String[] pathParts = pathInfo.split("/");
	    if (pathParts.length < 2) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        return;
	    }

	    int sessionId = -1;
	    try {
	        sessionId = Integer.parseInt(pathParts[1]);
	    } catch (NumberFormatException e) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        return;
	    }

	    // 해당 sessionID의 세션 정보 가져오기
	    SessionDto session = sessionService.getSessionById(sessionId);
	    if (session == null) {
	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        return;
	    }

	    // JSON 응답 생성
	    JSONObject sessionJson = new JSONObject();
	    sessionJson.put("session_id", session.getSession_id());
	    sessionJson.put("query_id", session.getQuery_id());
	    sessionJson.put("start_at", session.getStart_at().toString());
	    sessionJson.put("end_at", session.getEnd_at().toString());
	    sessionJson.put("problem_pool", session.getProblem_pool());

	    // 참가자 정보 추가
	    List<Integer> participants = sessionService.getSessionParticipants(sessionId);
	    JSONArray participantsArray = new JSONArray();
	    for (int userId : participants) {
	        JSONObject participantJson = new JSONObject();
	        participantJson.put("user_id", userId);
	        participantsArray.put(participantJson);
	    }
	    sessionJson.put("participants_cnt", participants.size());
	    sessionJson.put("session_participants", participantsArray);

	    // 문제 정보 추가
	    List<Integer> problems = sessionService.getSessionProblems(sessionId);
	    JSONArray problemsArray = new JSONArray();
	    for (int problemId : problems) {
	        JSONObject problemJson = new JSONObject();
	        problemJson.put("problem_id", problemId);
	        problemsArray.put(problemJson);
	    }
	    sessionJson.put("problems_cnt", problems.size());
	    sessionJson.put("session_problems", problemsArray);

	    // 응답 반환
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(sessionJson.toString());
	    out.flush();
	}


	// 스터디ID를 사용하여 해당 스터디에 속한 세션 정보 가져오기
	private void getSessionsByStudyId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// studyID 추출
		String pathInfo = request.getPathInfo();
		String[] pathParts = pathInfo.split("/");
		if (pathParts.length < 3) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int studyId = -1;
		try {
			studyId = Integer.parseInt(pathParts[2]);
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// 해당 스터디에 대한 세션 목록 가져오기
		List<SessionDto> sessions = sessionService.getSessionsByStudyId(studyId);
		if (sessions == null || sessions.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// JSON 응답 생성
		JSONObject responseBody = new JSONObject();
		JSONArray sessionArray = new JSONArray();

		for (SessionDto session : sessions) {
			JSONObject sessionJson = new JSONObject();
			sessionJson.put("session_id", session.getSession_id());
			sessionJson.put("query_id", session.getQuery_id());
			sessionJson.put("start_at", session.getStart_at().toString());
			sessionJson.put("end_at", session.getEnd_at().toString());
			sessionJson.put("problem_pool", session.getProblem_pool());

			// 참가자 정보 추가
			List<Integer> participants = sessionService.getSessionParticipants(session.getSession_id());
			JSONArray participantsArray = new JSONArray();
			for (int userId : participants) {
				JSONObject participantJson = new JSONObject();
				participantJson.put("user_id", userId);
				participantsArray.put(participantJson);
			}
			sessionJson.put("participants_cnt", participants.size());
			sessionJson.put("session_participants", participantsArray);

			// 문제 정보 추가
			List<Integer> problems = sessionService.getSessionProblems(session.getSession_id());
			JSONArray problemsArray = new JSONArray();
			for (int problemId : problems) {
				JSONObject problemJson = new JSONObject();
				problemJson.put("problem_id", problemId);
				problemsArray.put(problemJson);
			}
			sessionJson.put("problems_cnt", problems.size());
			sessionJson.put("session_problems", problemsArray);

			sessionArray.put(sessionJson);
		}

		responseBody.put("num_elements", sessions.size());
		responseBody.put("sessions", sessionArray);

		// 응답 반환
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(responseBody.toString());
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path != null) {
			if (path.startsWith("/register")) {
				// post : /session/register
				int session_id = parseAndRegisterSession(request, response);
				if (session_id == -1) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
				} else {
					response.setStatus(HttpServletResponse.SC_CREATED); // 201
				}
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				// body 생성
				JSONObject body = new JSONObject();
				body.put("session_id", session_id);

				// response 출력
				PrintWriter out = response.getWriter();
				out.print(body.toString());
				out.flush();
			}

			else if (path.startsWith("/participate")) {
				// post : /session/participate
				int result = participateInSession(request, response);
				if (result == -1) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
				} else {
					response.setStatus(HttpServletResponse.SC_OK); // 200
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");

					// body 생성
					JSONObject body = new JSONObject();
					body.put("session_id", request.getParameter("session_id"));
					body.put("user_id", request.getParameter("user_id"));

					// response 출력
					PrintWriter out = response.getWriter();
					out.print(body.toString());
					out.flush();
				}

			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
		}

	}

	private int participateInSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// request 파싱
		StringBuilder jsonBuffer = new StringBuilder();
		String line;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			jsonBuffer.append(line);
		}

		// 문자열을 JSONObject로 변환
		JSONObject jsonObject = new JSONObject(jsonBuffer.toString());

		// session_id와 user_id 추출
		int sessionId = jsonObject.getInt("session_id");
		int userId = jsonObject.getInt("user_id");

		// Service 호출하여 데이터 저장
		int result = sessionService.addParticipant(sessionId, userId);

		if (result == -1) {
			return -1;
		}

		// 응답 생성
		JSONObject body = new JSONObject();
		body.put("session_id", sessionId);
		body.put("user_id", userId);
//	    body.put("participants", participantsArray);

		// 응답 출력
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(body.toString());
		out.flush();
		out.close(); // 추가

		return 1;
	}

	private int parseAndRegisterSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuilder jsonBuffer = new StringBuilder();
		String line;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			jsonBuffer.append(line);
		}

		// 문자열을 JSONObject로 변환
		JSONObject jsonObject = new JSONObject(jsonBuffer.toString());

		// 각 필드 파싱
		int studyId = jsonObject.getInt("study_id");
		int queryId = jsonObject.getInt("query_id");
		String problemPool = jsonObject.getString("problemPool");

		// 타임스탬프 파싱
		Timestamp startAt = null;
		Timestamp endAt = null;

		// SimpleDateFormat을 사용하여 문자열을 java.util.Date로 파싱
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date s = dateFormat.parse(jsonObject.getString("start_at"));
			java.util.Date e = dateFormat.parse(jsonObject.getString("end_at"));
			// java.util.Date를 java.sql.Timestamp로 변환
			startAt = new Timestamp(s.getTime());
			endAt = new Timestamp(e.getTime());
		} catch (ParseException e) {
			System.out.println("[ERROR] timestamp 파싱 에러");
			e.printStackTrace();
			return -1; // must raise error
		}

		// Dto 생성
		SessionDto session = new SessionDto(-1, studyId, queryId, startAt, endAt, problemPool);

		// Servcie 호출
		return sessionService.RegisterSession(session);
	}
}
