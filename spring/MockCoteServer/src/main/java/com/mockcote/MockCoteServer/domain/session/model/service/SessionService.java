package com.mockcote.MockCoteServer.domain.session.model.service;

import java.util.List;

import com.mockcote.MockCoteServer.domain.session.dto.Session;

public interface SessionService {
	/**
	 * 특정 스터디의 모든 세션 정보 조회
	 * @param studyId
	 * @return
	 */
	List<Session> getSessionsByStudyId(int studyId);

}
