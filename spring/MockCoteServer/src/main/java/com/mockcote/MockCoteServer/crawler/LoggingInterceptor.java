package com.mockcote.MockCoteServer.crawler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    // 컨트롤러 메서드가 호출되기 전에 실행
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("HTTP Method: " + request.getMethod());
        System.out.println("Handler: " + handler);
        System.out.println("Request Parameters: " + request.getParameterMap());
        return true; // true로 설정하면 요청이 컨트롤러로 전달됨
    }

    // 요청 완료 후에 실행 (뷰 렌더링 이후)
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            System.out.println("Exception occurred: " + ex.getMessage());
        }
        System.out.println("Request completed");
    }
}
