package dev.qna.qna_auth_service.service;

import dev.qna.qna_auth_service.dto.AuthRequestDTO;
import dev.qna.qna_auth_service.dto.AuthResponseDTO;
import dev.qna.qna_auth_service.dto.BaseResponseDTO;
import dev.qna.qna_auth_service.repository.UserRepository;
import dev.qna.qna_auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import dev.qna.qna_auth_service.model.User;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public BaseResponseDTO<AuthResponseDTO> register(AuthRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new BaseResponseDTO<>(false, "Email already registered", null);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        AuthResponseDTO responseDTO = new AuthResponseDTO(token);
        return new BaseResponseDTO<>(true, "Username registered successfully", responseDTO);
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token);
    }

}
