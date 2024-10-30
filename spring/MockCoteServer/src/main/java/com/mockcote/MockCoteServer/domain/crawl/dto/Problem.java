package com.mockcote.MockCoteServer.domain.crawl.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    private int problemId;
    private int difficulty;
    private String title;
}
