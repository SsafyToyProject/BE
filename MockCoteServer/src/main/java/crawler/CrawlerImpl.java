package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.dao.LiveSessionDaoImpl;
import model.dto.ProblemDto;
import model.dto.SessionDto;
import model.dto.SessionTrackerDto;
import model.dto.UserDto;

public class CrawlerImpl implements Crawler {
	
	private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";
	// singleton

	static Crawler instance = new CrawlerImpl();

	public static Crawler getInstance() {
		return instance;
	}

	private CrawlerImpl() {
	}

	// Override functions

	@Override
	public Set<Integer> getSolvedProblemsByHandle(List<String> handles) {
		Set<Integer> ret = new HashSet<>();
		// Define user-agent header
		for (String handle : handles) {
			String url = "https://www.acmicpc.net/user/" + handle;
			try (CloseableHttpClient client = HttpClients.createDefault()) {
				// Send GET request with headers
				HttpGet request = new HttpGet(url);
				request.setHeader("User-Agent", userAgent);
				HttpResponse response = client.execute(request);

				// Parse the response body using Jsoup
				String responseBody = EntityUtils.toString(response.getEntity());
				Document doc = Jsoup.parse(responseBody);

				// Get the page's text content
				String text = doc.text();

				// Find the '맞은 문제' section
				int start = text.indexOf("맞은 문제") + "맞은 문제".length();
				text = text.substring(start);

				// Find the second occurrence of '맞은 문제'
				start = text.indexOf("맞은 문제") + "맞은 문제".length();
				text = text.substring(start);

				// Find the end marker '시도했지만 맞지 못한 문제'
				int end = text.indexOf("시도했지만 맞지 못한 문제");
				String problemText = text.substring(0, end);

				// Extract all problem numbers using regex
				Pattern pattern = Pattern.compile("\\d+");
				Matcher matcher = pattern.matcher(problemText);
				List<Integer> problemNumbers = new ArrayList<>();
				while (matcher.find()) {
					problemNumbers.add(Integer.parseInt(matcher.group()));
				}

				// Add to global solved list
				ret.addAll(problemNumbers);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public List<ProblemDto> executeQuery(String query) {
		List<ProblemDto> ret = new ArrayList<>();
		int page = 1;

		while (true) {
			try (CloseableHttpClient client = HttpClients.createDefault()) {
				HttpGet request = new HttpGet(query + page + "&sort=id");
				HttpResponse response = client.execute(request);
				String responseBody = EntityUtils.toString(response.getEntity());

				// Parse the JSON response
				JSONObject jsonResponse = new JSONObject(responseBody);

				// Stop if there are no more problems
				int count = jsonResponse.getInt("count");
				if (count == 0)
					break;

				// Parse items
				JSONArray items = jsonResponse.getJSONArray("items");

				// Store problemDto in an array
				for (int i = 0; i < items.length(); i++) {
					JSONObject item = items.getJSONObject(i);
					ret.add(new ProblemDto(item.getInt("problemId"), item.getInt("level"), item.getString("titleKo")));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			page++;
		}

		return ret;
	}

	@Override
	public void liveTrack() {
		List<SessionDto> activeSessions = LiveSessionDaoImpl.getInstance().getActiveSessions();
		for(SessionDto session : activeSessions) {
			for(ProblemDto problem : session.getSessionProblem()) {
				String url = "https://www.acmicpc.net/status?problem_id="
							+ problem.getProblem_id()
							+ "&user_id=&language_id=-1&result_id=4&from_problem=1";
				try (CloseableHttpClient client = HttpClients.createDefault()) {
					// Send GET request with headers
					HttpGet request = new HttpGet(url);
					request.setHeader("User-Agent", userAgent);
					HttpResponse response = client.execute(request);

					// Parse the response body using Jsoup
					String responseBody = EntityUtils.toString(response.getEntity());
					
					// Parse to status_record
					List<status_record> records = parse_record(responseBody);
					
					// Find records who need update
					for(status_record rec : records) {
						for(UserDto user : session.getSessionParticipant()) {
							if(rec.handle.equals(user.getHandle())) {
								SessionTrackerDto dto = new SessionTrackerDto(session.getSession_id(), user.getUser_id(),
										problem.getProblem_id(), "", rec.performance,
										rec.language, rec.submission_id+"", "");
								LiveSessionDaoImpl.getInstance().updateTracker(dto);
								break;
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class status_record {
		int submission_id; //제출 번호
		String handle; //아이디
		int performance; //실행 시간
		String language; //언어
	}
	
	private List<status_record> parse_record(String text) {
        List<status_record> ret = new ArrayList<>();

        // JSoup을 사용해 HTML 텍스트 파싱
        Document doc = Jsoup.parse(text);

        // status-table ID를 가진 테이블의 tbody를 선택
        Element table = doc.getElementById("status-table");
        Elements rows = table.select("tbody tr"); // 각 제출 기록에 해당하는 행을 선택

        // 각 행(tr)을 순회하면서 데이터를 추출
        for (Element row : rows) {
            status_record record = new status_record();

            // 제출 번호
            record.submission_id = Integer.parseInt(row.select("td").get(0).text());

            // 아이디
            record.handle = row.select("td").get(1).text();

            // 실행 시간 (숫자만 추출)
            String timeText = row.select("td").get(5).text(); // 예: "0 ms"
            record.performance = Integer.parseInt(timeText.replaceAll("[^0-9]", "")); // "0 ms"에서 숫자만 추출

            // 언어
            record.language = row.select("td").get(6).text();

            // 리스트에 추가
            ret.add(record);
        }

        return ret;
    }
}
