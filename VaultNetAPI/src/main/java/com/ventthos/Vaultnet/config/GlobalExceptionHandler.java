package com.ventthos.Vaultnet.config;

import com.ventthos.Vaultnet.dto.responses.ApiResponse;
import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import org.springframework.web.servlet.NoHandlerFoundException;

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
                Code.VALIDATION_ERROR.name(),
                Code.VALIDATION_ERROR.getDefaultMessage(),
                errores
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                "Ruta no encontrada",
                Code.ROUTE_NOT_FOUND.name(),
                Code.ROUTE_NOT_FOUND.getDefaultMessage() + ex.getRequestURL(),
                null
        ));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiError(ApiException ex){
        return ResponseEntity.status(ex.getHttpCode()).body(new ApiResponse<>(
                "Error",
                ex.getCode().name(),
                ex.getMessage(),
                null
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception e) {
        return ResponseEntity.internalServerError().body(new ApiResponse<>(
                "Error",
                Code.INTERNAL_ERROR.name(),
                Code.INTERNAL_ERROR.getDefaultMessage(),
                null
        ));
    }

    @ExceptionHandler(org.springframework.web.multipart.MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleFileTooBig(Exception e){
        return ResponseEntity.internalServerError().body(new ApiResponse<>(
                "Error",
                Code.FILE_TOO_BIG.name(),
                Code.FILE_TOO_BIG.getDefaultMessage(),
                null
        ));
    }

    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleRequestNotSupported(Exception e){
        return ResponseEntity.internalServerError().body(new ApiResponse<>(
                "Error",
                Code.METHOD_NOT_ALLOWED.name(),
                Code.METHOD_NOT_ALLOWED.getDefaultMessage(),
                null
        ));
    }
}