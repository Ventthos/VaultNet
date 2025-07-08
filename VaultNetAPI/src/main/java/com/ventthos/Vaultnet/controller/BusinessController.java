package com.ventthos.Vaultnet.controller;

import com.ventthos.Vaultnet.config.JwtUtil;
import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.business.BusinessResponseDto;
import com.ventthos.Vaultnet.dto.business.CreateBusinessDto;
import com.ventthos.Vaultnet.dto.responses.ApiResponse;
import com.ventthos.Vaultnet.dto.user.UserResponseDto;
import com.ventthos.Vaultnet.service.BusinessService;
import com.ventthos.Vaultnet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@RestController
@RequestMapping("/business")
public class BusinessController {
    private final BusinessService businessService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public BusinessController(BusinessService businessService, JwtUtil jwtUtil, UserService userService){
        this.businessService = businessService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BusinessResponseDto>> createBusiness(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CreateBusinessDto businessDto) {

        // Extraer token y obtener ID del usuario
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);

        // Crear el negocio
        BusinessResponseDto businessResponse = businessService.CreateBusiness(businessDto, userId);

        return ResponseEntity
                .ok(new ApiResponse<>(
                        "Success",
                        "Negocio creado correctamente",
                        businessResponse
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BusinessResponseDto>> getBusiness(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) throws IllegalAccessException {
        // Extraer token y obtener ID del usuario
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);

        if(userService.getUserBusinesses(userId).businesses().stream()
                .noneMatch(userBusinessInResponse -> userBusinessInResponse.businessId().equals(id)))
        {
            throw new IllegalAccessException("El usuario no pertenece al negocio con id " + id);
        }

        BusinessResponseDto businessResponse = businessService.GetBussiness(id);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        "Negocio obtenido correctamente",
                        businessResponse
                )
        );
    }
}
