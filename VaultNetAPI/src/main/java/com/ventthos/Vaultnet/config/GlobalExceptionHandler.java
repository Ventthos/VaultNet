package com.ventthos.Vaultnet.config;

import com.ventthos.Vaultnet.dto.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Extraer solo los mensajes personalizados de error
        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ApiResponse<List<String>> response = new ApiResponse<>(
                "Error",
                "Error de validación",
                errores
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(
                "Error",
                e.getMessage(),
                null
        ));
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalAccess(IllegalAccessException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(
                "Acceso denegado",
                e.getMessage(),
                null
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception e) {
        return ResponseEntity.internalServerError().body(new ApiResponse<>(
                "Error",
                "Ocurrió un error inesperado.",
                null
        ));
    }
}