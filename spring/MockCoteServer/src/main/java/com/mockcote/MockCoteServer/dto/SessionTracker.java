package com.mockcote.MockCoteServer.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionTracker {
    private int sessionId;
    private int userId;
    private int problemId;
    private Timestamp solvedAt;
    private int performance;
    private String language;
    private String codeLink;
    private String description;
}
