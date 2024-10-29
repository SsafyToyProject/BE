package com.mockcote.MockCoteServer.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mockcote.MockCoteServer.dto.SessionTracker;

@Mapper
public interface SessionTrackerMapper {
	SessionTracker findSessionTracker(@Param("sessionId") int sessionId, @Param("userId") int userId, @Param("problemId") int problemId);
	

}
