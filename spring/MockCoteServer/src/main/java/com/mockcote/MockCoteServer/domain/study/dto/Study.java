package com.mockcote.MockCoteServer.domain.study.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.mockcote.MockCoteServer.domain.user.dto.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Study {
    private int studyId;
    private int ownerId;
    private String name;
    private String description;
    private String code;
    private List<User> studyMembers;
    
}
