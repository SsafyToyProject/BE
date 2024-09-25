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

import model.dto.ProblemDto;

public class CrawlerImpl implements Crawler {

	// singleton

	static Crawler instance = new CrawlerImpl();

	static Crawler getInstance() {
		return instance;
	}

	private CrawlerImpl() {
	}

	// Override functions

	@Override
	public Set<Integer> getSolvedProblemsByHandle(List<String> handles) {
		Set<Integer> ret = new HashSet<>();
		// Define user-agent header
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";
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
	
	
}
