package com.mockcote.MockCoteServer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
    
    @GetMapping("/example")  // 경로 수정
    public ResponseEntity<Map<String, Object>> getExample() {
        
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("message", "This is a custom JSON response.");
        responseBody.put("data", new HashMap<String, String>() {{
            put("key1", "value1");
            put("key2", "value2");
        }});
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomHeaderValue");
        System.out.println(headers);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseBody);
    }
}
