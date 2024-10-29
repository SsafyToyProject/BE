package com.mockcote.MockCoteServer.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mockcote.MockCoteServer.dto.Session;

@Mapper
public interface SessionMapper {
	List<Session> findSessionsByStudyId(int studyId);

}
