package dev.qna.qna_auth_service.service;

import dev.qna.qna_auth_service.dto.AuthRequestDTO;
import dev.qna.qna_auth_service.dto.AuthResponseDTO;
import dev.qna.qna_auth_service.repository.UserRepository;
import dev.qna.qna_auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import dev.qna.qna_auth_service.model.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public String register(AuthRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists";
        }

        User user = User.builder()
                .username(request.getUsername())
                .passwordHash(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        return "User registered";
    }


    public AuthResponseDTO login(AuthRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponseDTO(token);
    }

}
