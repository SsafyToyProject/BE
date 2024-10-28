package com.mockcote.MockCoteServer.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private int sessionId;
    private int studyId;
    private int queryId;
    private Timestamp startAt;
    private Timestamp endAt;
    private String problemPool;
    
    private List<User> sessionParticipants;
    private List<Problem> sessionProblems;
}
