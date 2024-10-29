package com.mockcote.MockCoteServer.model.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrawlerScheduler {
	private final CrawlService crawlService;
	
	@Scheduled(fixedRate = 60000)
	public void crawlAndUpdateSessions() {
		crawlService.triggerSession();
	}
}
