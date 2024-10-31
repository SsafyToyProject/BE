package com.mockcote.MockCoteServer.domain.study.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Study {
    private int studyId;
    private int ownerId;
    private String name;
    private String description;
    private String code;
    private List<?> studyMembers;
    
}
