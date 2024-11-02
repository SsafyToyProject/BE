package com.mockcote.MockCoteServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // CSRF 비활성화 (테스트 환경에서만 사용)
            .authorizeHttpRequests()  // 변경된 메서드 사용
                .requestMatchers("/user", "/user/login").permitAll()  // 회원가입, 로그인 엔드포인트 허용
                .anyRequest().authenticated()
            .and()
            .httpBasic().disable();  // 기본 인증 비활성화
        return http.build();
    }
}
