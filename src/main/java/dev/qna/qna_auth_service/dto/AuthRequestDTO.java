package dev.qna.qna_auth_service.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username;
    private String email;
    private String password;

}
