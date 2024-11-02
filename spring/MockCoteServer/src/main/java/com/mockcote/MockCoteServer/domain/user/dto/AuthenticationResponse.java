package com.mockcote.MockCoteServer.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private int userId;
    private String handle;
    private int level;
    private String token;
}