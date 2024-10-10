package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.SessionDto;
import service.SessionService;

@WebServlet(urlPatterns = {"/session/*"})
public class SessionController extends HttpServlet {
	
	private SessionService sessionService = SessionService.getInstance();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if(path != null && path.startsWith("/register")) {
			//post : /session/register
			int session_id = parseAndRegisterSession(request, response);
	        if(session_id == -1) {
	        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400
	        } else {
	        	response.setStatus(HttpServletResponse.SC_CREATED); //201
	        }
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        
	        //body 생성
	        JSONObject body = new JSONObject();
	        body.put("session_id", session_id);
	        
	        //response 출력
	        PrintWriter out = response.getWriter();
	        out.print(body.toString());
	        out.flush();
		} else {
			
		}
	}
	
	private int parseAndRegisterSession (HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            return -1; //must raise error
        }
        
        //Dto 생성
        SessionDto session = new SessionDto(-1, studyId, queryId, startAt, endAt, problemPool);
        
        //Servcie 호출
		return sessionService.RegisterSession(session);
	}
}
