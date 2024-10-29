package com.mockcote.MockCoteServer.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Query {
    private int queryId;
    private String title;
    private String queryStr;
    private int numProblems;
    private List<Problem> problems;
}
