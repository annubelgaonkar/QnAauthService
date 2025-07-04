package dev.qna.qna_auth_service.exception;

import dev.qna.qna_auth_service.dto.BaseResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponseDTO<Object>> handleUserNotFound(UserNotFoundException ex){
        return new ResponseEntity<>(new BaseResponseDTO<>(
                false,
                ex.getMessage(),
                null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<BaseResponseDTO<Object>> handleInvalidCredentials(
            InvalidCredentialsException ex){
        return new ResponseEntity<>(new BaseResponseDTO<>(
                false,
                ex.getMessage(),
                null),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponseDTO<Object>> handleBadRequest(
            BadRequestException ex){
        return new ResponseEntity<>(new BaseResponseDTO<>(
                false,
                ex.getMessage(),
                null),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDTO<Object>> handleAllOtherExceptions(Exception ex){
        return new ResponseEntity<>(new BaseResponseDTO<>(
                false,
                ex.getMessage(),
                "Something went wrong"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
