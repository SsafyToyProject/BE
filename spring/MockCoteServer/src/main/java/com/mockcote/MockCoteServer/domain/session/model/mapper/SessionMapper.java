package com.mockcote.MockCoteServer.domain.session.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mockcote.MockCoteServer.domain.session.dto.Session;

@Mapper
public interface SessionMapper {
	/**
	 * 시작 시간이 3분 이하로 남은 세션 조회
	 * @return session list
	 */
	List<Session> searchReadySessions();
	
	/**
	 * 현재 진행중인 세션 조회
	 * @return session list
	 */
	List<Session> searchActiveSessions();
	
	/**
	 * session에 할당된 문제의 갯수 조회
	 * @param session_id
	 * @return 할당된 문제의 갯수
	 */
	int getProblemCount(int session_id);
	
	/**
	 * session 참가자 조회
	 * @param session_id
	 * @return user_id list
	 */
	List<Integer> getParticipants(int session_id);
	
	/**
	 * session에 할당된 문제 조회
	 * @param session_id
	 * @return problemId list
	 */
	List<Integer> getSessionProblemIds(int session_id);
	
	/**
	 * studyId로 모든 세션 정보 조회
	 * @param studyId
	 * @return
	 */
	List<Session> findSessionsByStudyId(int studyId);
}
