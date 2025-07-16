package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.config.FileRoutes;
import com.ventthos.Vaultnet.config.FileStorageService;
import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.Category;
import com.ventthos.Vaultnet.domain.Product;
import com.ventthos.Vaultnet.domain.Unit;
import com.ventthos.Vaultnet.dto.products.CreateProductDto;
import com.ventthos.Vaultnet.dto.products.ProductResponseDto;
import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import com.ventthos.Vaultnet.parsers.ProductParser;
import com.ventthos.Vaultnet.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BusinessService businessService;
    private final CategoryService categoryService;
    private final UnitService unitService;
    private final ProductParser productParser;
    private final FileStorageService fileStorageService;

    public ProductService(ProductRepository productRepository, BusinessService businessService, CategoryService categoryService,
    UnitService unitService, ProductParser productParser, FileStorageService fileStorageService){
        this.productRepository = productRepository;
        this.businessService = businessService;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.productParser = productParser;
        this.fileStorageService = fileStorageService;
    }

    public ProductResponseDto createProduct(CreateProductDto newProductDto, Long businessId, MultipartFile imageFile){

        String imagePath = null;

        if (imageFile != null && !imageFile.isEmpty()) {
            // Guardar la imagen
            imagePath = fileStorageService.save(imageFile, FileRoutes.PRODUCTS);
        }

        // Buscamos los componentes reales
        Business business = businessService.getBusinessOrTrow(businessId);
        Category category = categoryService.getCategoryOrThrow(newProductDto.categoryId());
        Unit unit = unitService.getUnitOrTrow(newProductDto.unitId());

        // Se forma el objeto
        Product newProduct = new Product();
        newProduct.setName(newProductDto.name());
        newProduct.setBusiness(business);
        newProduct.setDescription(newProductDto.description());
        newProduct.setImage(imagePath);
        newProduct.setQuantity(Objects.requireNonNullElse(newProductDto.quantity(), 0));
        newProduct.setUnit(unit);
        newProduct.setCategory(category);

        // Se guarda el objeto
        Product savedProduct = productRepository.save(newProduct);

        // Dentro del business, guardamos el objeto
        business.getProducts().add(savedProduct);

        // Devolvemos el objeto del usuario
        return productParser.toProductResponseDto(savedProduct);
    }

    public Product getProductOrThrow(Long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new ApiException(Code.PRODUCT_NOT_FOUND);
        }
        return optionalProduct.get();
    }

    public void confirmProductIsFromBusinessOrThrow(Long businessId, Long productId){
        Product product = getProductOrThrow(productId);
        if(!product.getBusiness().getBusinessId().equals(businessId)){
            throw new ApiException(Code.ACCESS_DENIED);
        }
    }

    public List<ProductResponseDto> getProductsFromBusiness(Long businessId){
        Business business = businessService.getBusinessOrTrow(businessId);
        return business.getProducts().stream().map(productParser::toProductResponseDto).toList();
    }

    public ProductResponseDto getProduct(Long productId){
        Product product = getProductOrThrow(productId);
        return productParser.toProductResponseDto(product);
    }
}
