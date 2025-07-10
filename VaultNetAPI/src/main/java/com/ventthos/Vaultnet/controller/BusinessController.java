package com.ventthos.Vaultnet.controller;

import com.ventthos.Vaultnet.config.JwtUtil;
import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.business.BusinessResponseDto;
import com.ventthos.Vaultnet.dto.business.CreateBusinessDto;
import com.ventthos.Vaultnet.dto.category.CategoryResponseDto;
import com.ventthos.Vaultnet.dto.category.CreateCategoryDto;
import com.ventthos.Vaultnet.dto.responses.ApiResponse;
import com.ventthos.Vaultnet.dto.user.UserResponseDto;
import com.ventthos.Vaultnet.service.BusinessService;
import com.ventthos.Vaultnet.service.CategoryService;
import com.ventthos.Vaultnet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/business")
public class BusinessController {
    private final BusinessService businessService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final CategoryService categoryService;

    public BusinessController(BusinessService businessService, JwtUtil jwtUtil, UserService userService,
                              CategoryService categoryService){
        this.businessService = businessService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BusinessResponseDto>> createBusiness(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CreateBusinessDto businessDto) {

        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);

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
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);

        userService.validateUserBelongsToBusiness(userId, id);

        BusinessResponseDto businessResponse = businessService.GetBussiness(id);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        "Negocio obtenido correctamente",
                        businessResponse
                )
        );
    }

    @PostMapping("/{id}/categories")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> postCategory(@PathVariable Long id,
                                                                         @RequestHeader("Authorization") String authHeader,
                                                                         @RequestBody @Valid CreateCategoryDto newCategoryBody)
    throws IllegalAccessException{
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);

        userService.validateUserBelongsToBusiness(userId, id);

        CategoryResponseDto categoryResponse = categoryService.createCategory(newCategoryBody, id);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        "Categoría creada correctamente",
                        categoryResponse
                )
        );

    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> getCategoriesFromBusiness(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    )throws IllegalAccessException{
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);
        userService.validateUserBelongsToBusiness(userId, id);

        List<CategoryResponseDto> categories = categoryService.getCategoriesFromBusiness(id);

        return  ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        "Categoría creada correctamente",
                        categories
                )
        );
    }

    @GetMapping("/{id}/categories/{idCategory}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> getCategory(
            @PathVariable Long id,
            @PathVariable Long idCategory,
            @RequestHeader("Authorization") String authHeader
    ) throws IllegalAccessException{
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);
        userService.validateUserBelongsToBusiness(userId, id);

        categoryService.confirmCategoryIsFromBusinessOrTrow(id, idCategory);

        CategoryResponseDto category = categoryService.getCategory(idCategory);

        return  ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        "Categoría creada correctamente",
                        category
                )
        );
    }

}
