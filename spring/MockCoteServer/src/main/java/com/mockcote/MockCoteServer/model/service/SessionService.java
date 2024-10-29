package com.mockcote.MockCoteServer.model.service;

public interface SessionService {
	/**
	 * 시작 시간이 3분 미만으로 남은 세션들에 문제를 선정하고,
	 * 참가자와 문제에 해당하는 sessionTracker를 삽입함
	 */
	void triggerSession();
}
