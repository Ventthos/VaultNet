package com.ventthos.Vaultnet.controller;

import com.ventthos.Vaultnet.config.JwtUtil;
import com.ventthos.Vaultnet.dto.business.BusinessResponseDto;
import com.ventthos.Vaultnet.dto.business.CreateBusinessDto;
import com.ventthos.Vaultnet.dto.responses.ApiResponse;
import com.ventthos.Vaultnet.service.BusinessService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
public class BusinessController {
    private final BusinessService businessService;
    private final JwtUtil jwtUtil;

    public BusinessController(BusinessService businessService, JwtUtil jwtUtil){
        this.businessService = businessService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BusinessResponseDto>> createBusiness(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CreateBusinessDto businessDto) {

        try {
            // Extraer token y obtener ID del usuario
            String token = authHeader.substring(7);
            Long userId = jwtUtil.extractUserId(token);

            // Crear el negocio
            BusinessResponseDto businessResponse = businessService.CreateBusiness(businessDto, userId);

            return ResponseEntity
                    .ok(new ApiResponse<>(
                            "success",
                            "Negocio creado correctamente",
                            businessResponse
                    ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse<>(
                            "error",
                            e.getMessage(),
                            null
                    ));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new ApiResponse<>(
                            "error",
                            "Ocurrió un error inesperado " + e,
                            null
                    ));
        }
    }
}
