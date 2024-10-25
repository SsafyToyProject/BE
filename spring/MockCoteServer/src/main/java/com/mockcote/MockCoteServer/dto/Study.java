package com.mockcote.MockCoteServer.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

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
