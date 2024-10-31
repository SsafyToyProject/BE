package com.mockcote.MockCoteServer.domain.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeStudyResponse {
	private int studyId;
    private int ownerId;
    private String ownerHandle;
    private String name;
    private String description;
    private String code;
}
