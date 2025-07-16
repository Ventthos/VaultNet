package com.ventthos.Vaultnet.controller;

import com.ventthos.Vaultnet.config.JwtUtil;
import com.ventthos.Vaultnet.dto.business.BusinessResponseDto;
import com.ventthos.Vaultnet.dto.business.CreateBusinessDto;
import com.ventthos.Vaultnet.dto.category.CategoryResponseDto;
import com.ventthos.Vaultnet.dto.category.CreateCategoryDto;
import com.ventthos.Vaultnet.dto.change.ChangeResponseDto;
import com.ventthos.Vaultnet.dto.products.CreateProductDto;
import com.ventthos.Vaultnet.dto.products.ProductResponseDto;
import com.ventthos.Vaultnet.dto.responses.ApiResponse;
import com.ventthos.Vaultnet.dto.unit.CreateUnitDto;
import com.ventthos.Vaultnet.dto.unit.UnitResponseDto;
import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import com.ventthos.Vaultnet.service.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    private final BusinessService businessService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private final UnitService unitService;
    private final ProductService productService;
    private final ChangeService changeService;

    public BusinessController(BusinessService businessService, JwtUtil jwtUtil, UserService userService,
                              CategoryService categoryService, UnitService unitService, ProductService productService,
                              ChangeService changeService){
        this.businessService = businessService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.productService = productService;
        this.changeService = changeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BusinessResponseDto>> createBusiness(
            @RequestHeader("Authorization") String authHeader,
            @ModelAttribute @Valid CreateBusinessDto businessDto,
            @RequestPart(value = "image",required = false)MultipartFile image) {

        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);

        // Crear el negocio
        BusinessResponseDto businessResponse = businessService.CreateBusiness(businessDto, userId, image);

        return ResponseEntity
                .created(URI.create("/business/"+userId)).body(new ApiResponse<>(
                        "Success",
                        Code.BUSINESS_CREATED.name(),
                        Code.BUSINESS_CREATED.getDefaultMessage(),
                        businessResponse
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BusinessResponseDto>> getBusiness(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);

        userService.validateUserBelongsToBusiness(userId, id);

        BusinessResponseDto businessResponse = businessService.GetBussiness(id);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        Code.ELEMENT_GET_SUCCESSFUL.name(),
                        Code.ELEMENT_GET_SUCCESSFUL.getDefaultMessage(),
                        businessResponse
                )
        );
    }

    @PostMapping("/{id}/categories")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> postCategory(@PathVariable Long id,
                                                                         @RequestHeader("Authorization") String authHeader,
                                                                         @RequestBody @Valid CreateCategoryDto newCategoryBody)
    {
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);

        userService.validateUserBelongsToBusiness(userId, id);

        CategoryResponseDto categoryResponse = categoryService.createCategory(newCategoryBody, id);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        Code.CATEGORY_CREATED.name(),
                        Code.CATEGORY_CREATED.getDefaultMessage(),
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
                        Code.ELEMENT_GET_SUCCESSFUL.name(),
                        Code.ELEMENT_GET_SUCCESSFUL.getDefaultMessage(),
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
                        Code.ELEMENT_GET_SUCCESSFUL.name(),
                        Code.ELEMENT_GET_SUCCESSFUL.getDefaultMessage(),
                        category
                )
        );
    }

    @PostMapping("/{id}/units")
    public ResponseEntity<ApiResponse<UnitResponseDto>> createUnit(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CreateUnitDto createUnitDto
    ) throws IllegalAccessException {
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);
        userService.validateUserBelongsToBusiness(userId, id);

        UnitResponseDto unit = unitService.createUnit(createUnitDto, id);
        return  ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        Code.UNIT_CREATED.name(),
                        Code.UNIT_CREATED.getDefaultMessage(),
                        unit
                )
        );
    }

    @GetMapping("/{id}/units")
    public ResponseEntity<ApiResponse<List<UnitResponseDto>>> getUnitsFromBusiness(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) throws IllegalAccessException {
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);
        userService.validateUserBelongsToBusiness(userId, id);

        List<UnitResponseDto> units = unitService.getUnitsFromBusiness(id);

        return  ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        Code.ELEMENT_GET_SUCCESSFUL.name(),
                        Code.ELEMENT_GET_SUCCESSFUL.getDefaultMessage(),
                        units
                )
        );
    }

    @GetMapping("/{id}/units/{unitId}")
    public ResponseEntity<ApiResponse<UnitResponseDto>> getUnit(
            @PathVariable Long id,
            @PathVariable Long unitId,
            @RequestHeader("Authorization") String authHeader
    ) {
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);
        userService.validateUserBelongsToBusiness(userId, id);

        unitService.confirmUnitIsFromBusinessOrTrow(id, unitId);

        UnitResponseDto unit = unitService.getUnit(id);
        return  ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        Code.ELEMENT_GET_SUCCESSFUL.name(),
                        Code.ELEMENT_GET_SUCCESSFUL.getDefaultMessage(),
                        unit
                )
        );
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @ModelAttribute @Valid CreateProductDto productDto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IllegalAccessException {
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);
        userService.validateUserBelongsToBusiness(userId, id);

        unitService.confirmUnitIsFromBusinessOrTrow(id, productDto.unitId());
        categoryService.confirmCategoryIsFromBusinessOrTrow(id, productDto.categoryId());

        ProductResponseDto responseDto = productService.createProduct(productDto, id, imageFile);
        return  ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        Code.PRODUCT_CREATED.name(),
                        Code.PRODUCT_CREATED.getDefaultMessage(),
                        responseDto
                )
        );
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getProductsFromBusiness(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ){
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);
        userService.validateUserBelongsToBusiness(userId, id);

        List<ProductResponseDto> responseDto = productService.getProductsFromBusiness(id);
        return  ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        Code.ELEMENT_GET_SUCCESSFUL.name(),
                        Code.ELEMENT_GET_SUCCESSFUL.getDefaultMessage(),
                        responseDto
                )
        );
    }

    @GetMapping("/{id}/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(
        @PathVariable Long id,
        @PathVariable Long productId,
        @RequestHeader("Authorization") String authHeader
    ){
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);
        userService.validateUserBelongsToBusiness(userId, id);

        productService.confirmProductIsFromBusinessOrThrow(id, productId);

        ProductResponseDto responseDto = productService.getProduct(productId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        Code.ELEMENT_GET_SUCCESSFUL.name(),
                        Code.ELEMENT_GET_SUCCESSFUL.getDefaultMessage(),
                        responseDto
                )
        );
    }

    @GetMapping("/{id}/products/{productId}/changes")
    public ResponseEntity<ApiResponse<List<ChangeResponseDto>>> getChangesInProduct(
            @PathVariable Long id,
            @PathVariable Long productId,
            @RequestHeader("Authorization") String authHeader
    ){
        // Extraer token y obtener ID del usuario
        Long userId = jwtUtil.extractUserIdFromHeader(authHeader);
        userService.validateUserBelongsToBusiness(userId, id);
        productService.confirmProductIsFromBusinessOrThrow(id, productId);

        List<ChangeResponseDto> responseDto = changeService.getChangesFromProduct(productId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Success",
                        Code.ELEMENT_GET_SUCCESSFUL.name(),
                        Code.ELEMENT_GET_SUCCESSFUL.getDefaultMessage(),
                        responseDto
                )
        );
    }
}
