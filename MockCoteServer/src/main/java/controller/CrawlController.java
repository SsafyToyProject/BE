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
import model.dto.ProblemDto;
import model.dto.QueryDto;
import service.QueryService;

@WebServlet(urlPatterns = {"/crawl/*"})
public class CrawlController extends HttpServlet {
    private QueryService service = QueryService.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (path != null) {
            // [크롤] 문제 정보 조회: GET /crawl/problem/{problemID}
            if (path.startsWith("/problem/")) {
                String problemId = path.substring("/problem/".length());
                
                ProblemDto problem = service.getProblemInfo(Integer.parseInt(problemId));
                
                response.setStatus(HttpServletResponse.SC_OK);
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("problem_id", problem.getProblem_id());
                jsonResponse.put("difficulty", problem.getDifficulty());
                jsonResponse.put("title", problem.getTitle());
                PrintWriter out = response.getWriter();
                out.print(jsonResponse.toString());
                out.flush();
                
            // [크롤] 등록된 쿼리 목록 조회: GET /crawl/query
            } else if (path.equals("/query")) {
                // 등록된 쿼리 목록 조회 로직 수행 (예시)
            	List<QueryDto> list = service.getQueryList();
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("num_elements", list.size());
                
                //list 담기
                JSONArray itemsArray = new JSONArray();
                for (QueryDto query : list) {
                    JSONObject queryJson = new JSONObject();
                    queryJson.put("query_id", query.getQuery_id());
                    queryJson.put("title", query.getTitle());
                    queryJson.put("num_problems", query.getNum_problems());
                    itemsArray.put(queryJson); // JSON 객체를 JSONArray에 추가
                }
                jsonResponse.put("items", itemsArray);
                
                // 응답 작성
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.print(jsonResponse.toString());
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (path != null) {
            // [크롤] 신규 쿼리 등록: POST /crawl/query
            if (path.equals("/query")) {
                StringBuilder jsonBuffer = new StringBuilder();
                String line;
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }

                // JSON 데이터 파싱
                JSONObject jsonRequest = new JSONObject(jsonBuffer.toString());
                // 쿼리 등록 로직 수행
                String title = jsonRequest.getString("title");
                String query_str = jsonRequest.getString("query_str");
                int[] ret = service.insertQuery(title,query_str);
                int query_id = ret[0];
                int num_problems = ret[1];

                // 응답 작성
                JSONObject jsonResponse = new JSONObject();
                if (query_id != 0) {
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    jsonResponse.put("query_id", query_id);
                    jsonResponse.put("num_problems", num_problems);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }

                PrintWriter out = response.getWriter();
                out.print(jsonResponse.toString());
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
	}

}
