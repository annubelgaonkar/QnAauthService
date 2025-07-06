package dev.qna.qna_auth_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // <-- Exclude nulls in JSON
public class AuthResponseDTO {
    private String token;
    private String username;        // for register only, can be null for login

    public AuthResponseDTO() {

    }
    public AuthResponseDTO(String token){
        this.token = token;
    }

    public AuthResponseDTO(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
