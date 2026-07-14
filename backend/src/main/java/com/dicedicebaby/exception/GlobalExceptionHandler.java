package com.dicedicebaby.exception;

import com.dicedicebaby.dto.response.ApiErrorResponseDTO;
import com.dicedicebaby.enums.ApiErrorCode;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    // Get first error message from DTO
    String errorMessage = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();

    Map<String, String> response = new HashMap<>();
    response.put("message", errorMessage);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  // Handle IllegalStateException (Email already exist, etc.)
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, String>> handleIllegalState(IllegalStateException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, String>> handleDataIntegrity(
      DataIntegrityViolationException ex) {
    Map<String, String> response = new HashMap<>();

    // Check if error come from player username
    String message = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "";

    if (message.contains("players_player_username_key") || message.contains("player_username")) {
      response.put("message", "Ce pseudo est déjà utilisé par un autre joueur.");
    } else {
      response.put("message", "Une erreur de base de données est survenue.");
    }

    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiErrorResponseDTO> handleApiException(ApiException ex) {

    ApiErrorCode errorCode = ex.getErrorCode();

    ApiErrorResponseDTO response =
        new ApiErrorResponseDTO(
            errorCode.getStatus().value(), errorCode.name(), errorCode.getMessage());

    return ResponseEntity.status(errorCode.getStatus()).body(response);
  }
}
