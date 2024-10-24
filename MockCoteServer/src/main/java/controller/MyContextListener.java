package controller;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import crawler.Crawler;
import crawler.CrawlerImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MyContextListener implements ServletContextListener {

    private Timer timer;

    // 애플리케이션 시작 시 호출
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext initialized");

        // 일정 시간마다 실행되는 크롤링 작업 스케줄링
        timer = new Timer(true); // 데몬 스레드로 타이머 생성
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 여기서 데이터베이스 조회 및 크롤링 작업 수행
                crawlAndUpdateSessions();
            }
        };

        // 1분마다 크롤링 작업 수행 (처음 지연 0, 1분 주기)
        timer.scheduleAtFixedRate(task, 0, 60000);
    }

    // 애플리케이션 종료 시 호출
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext destroyed");

        // 애플리케이션 종료 시 타이머 취소
        if (timer != null) {
            timer.cancel();
        }
    }
    
    private Crawler instance = CrawlerImpl.getInstance();
    // 실제 크롤링 및 세션 업데이트 로직
    private void crawlAndUpdateSessions() {
        instance.triggerTrack();
        instance.liveTrack();
        System.out.println("Crawling and session update completed at: " + LocalDateTime.now());
    }
}
