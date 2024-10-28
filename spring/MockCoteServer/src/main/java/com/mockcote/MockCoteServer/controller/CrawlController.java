package com.mockcote.MockCoteServer.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mockcote.MockCoteServer.dto.Problem;
import com.mockcote.MockCoteServer.dto.Query;
import com.mockcote.MockCoteServer.model.service.CrawlService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/crawl")
public class CrawlController {
	private final CrawlService crawlService;
	
    @GetMapping("/problem/{problem_id}")
    public ResponseEntity<Problem> getProblemById(@PathVariable("problem_id") int problemId){
    	Problem problem = crawlService.searchProblemById(problemId);
    	return ResponseEntity.ok(problem);
    }
    
    @GetMapping("/query")
    public ResponseEntity<List<Query>> searchQueries() {
    	return ResponseEntity.ok(crawlService.searchQueriesWithoutProblems());
    }
    
    @PostMapping("/query")
    public ResponseEntity<Map<String,Object>> insertQuery(@RequestBody Query query) {
    	Query created = crawlService.executeQuery(query);
    	Map<String, Object> response = new LinkedHashMap<>();
    	response.put("query_id", created.getQueryId());
    	response.put("num_problems", created.getNumProblems());
    	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    
}
