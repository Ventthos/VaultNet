package com.ventthos.Vaultnet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ventthos.Vaultnet.config.FileRoutes;
import com.ventthos.Vaultnet.config.FileStorageService;
import com.ventthos.Vaultnet.domain.*;
import com.ventthos.Vaultnet.dto.change.CreateChangeDto;
import com.ventthos.Vaultnet.dto.products.CreateProductDto;
import com.ventthos.Vaultnet.dto.products.PatchProductDto;
import com.ventthos.Vaultnet.dto.products.ProductResponseDto;
import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import com.ventthos.Vaultnet.parsers.ProductParser;
import com.ventthos.Vaultnet.repository.ProductRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BusinessService businessService;
    private final CategoryService categoryService;
    private final UnitService unitService;
    private final ProductParser productParser;
    private final FileStorageService fileStorageService;
    private final ChangeService changeService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    record ProductChangesDto(Map<String, Object> oldProductValues, Map<String, Object> newProductValues) { }

    public ProductService(ProductRepository productRepository, BusinessService businessService, CategoryService categoryService,
                          UnitService unitService, ProductParser productParser, FileStorageService fileStorageService, @Lazy ChangeService changeService,
                          UserService userService, @Lazy ObjectMapper objectMapper){
        this.productRepository = productRepository;
        this.businessService = businessService;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.productParser = productParser;
        this.fileStorageService = fileStorageService;
        this.changeService = changeService;
        this.userService = userService;
        this.objectMapper = objectMapper;

    }

    public ProductResponseDto createProduct(CreateProductDto newProductDto, Long businessId, MultipartFile imageFile,
    Long userId) throws JsonProcessingException {

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

        // Guardamos el cambio, que es solo el hecho de que se creó
        // Primero creando el map del nuevo producto
        Map<String, Object> newProductValues = new LinkedHashMap<>(){{
            put("name", savedProduct.getName());
            put("description", savedProduct.getDescription());
            put("quantity", savedProduct.getQuantity());
            put("categoryId", savedProduct.getCategory().getCategoryId());
            put("unitId", savedProduct.getUnit().getUnitId());
            put("image", savedProduct.getImage());
        }};

        // Se obtiene el usuario necesario
        User user = userService.getUserOrTrow(userId);

        changeService.createChange(new CreateChangeDto(
                savedProduct, user, "{}", objectMapper.writeValueAsString(newProductValues)
        ));

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

    public ProductResponseDto patchProduct(Long productId, PatchProductDto productDto, Long userId, MultipartFile imageFile)
            throws IOException {
        String imagePath = null;
        Product newProduct = getProductOrThrow(productId);
        ProductChangesDto changes = getChanges(newProduct, productDto);

        if(imageFile != null && !imageFile.isEmpty() && !isSameImage(imageFile, Path.of(newProduct.getImage()))){
            imagePath = fileStorageService.save(imageFile, FileRoutes.PRODUCTS);
            fileStorageService.deleteFile(Path.of(newProduct.getImage()));
            changes.oldProductValues.put("image", newProduct.getImage());
            changes.newProductValues.put("image", imagePath);
        }

        changes.newProductValues.forEach((key, value)->{
            switch (key){
                case "name" -> newProduct.setName((String) value);
                case "description" -> newProduct.setDescription((String) value);
                case "quantity" -> newProduct.setQuantity((Integer) value);
                case "categoryId" -> newProduct.setCategory(categoryService.getCategoryOrThrow((Long) value));
                case "unitId" -> newProduct.setUnit(unitService.getUnitOrTrow((Long) value));
                case "image" -> newProduct.setImage((String) value);
            }
        });
        Product changedProduct = productRepository.save(newProduct);

        // Guardado de cambios, necesitamos el user
        //Debe haber al menos un cambio
        if(!changes.newProductValues.isEmpty()){
            User user = userService.getUserOrTrow(userId);


            changeService.createChange(
                    new CreateChangeDto(changedProduct, user, objectMapper.writeValueAsString(changes.oldProductValues),
                            objectMapper.writeValueAsString(changes.newProductValues))
            );
        }


        return productParser.toProductResponseDto(changedProduct);
    }

    private ProductChangesDto getChanges(Product product, PatchProductDto productDto){
        Map<String, Object> oldProductValues = new LinkedHashMap<>();
        Map<String, Object> newProductValues = new LinkedHashMap<>();

        if(productDto.name() != null && !productDto.name().equals(product.getName())) {
            oldProductValues.put("name", product.getName());
            newProductValues.put("name", productDto.name());
        }
        if(productDto.description() != null && !productDto.description().equals(product.getDescription())){
            oldProductValues.put("description", product.getDescription());
            newProductValues.put("description", productDto.description());
        }
        if(productDto.quantity() != null && !productDto.quantity().equals(product.getQuantity())){
            oldProductValues.put("quantity", product.getQuantity());
            newProductValues.put("quantity", productDto.quantity());
        }
        if(productDto.categoryId() != null && !productDto.categoryId().equals(product.getCategory().getCategoryId())){
            oldProductValues.put("categoryId", product.getCategory().getCategoryId());
            newProductValues.put("categoryId", productDto.categoryId());
        }
        if(productDto.unitId() != null && !productDto.unitId().equals(product.getUnit().getUnitId())){
            oldProductValues.put("unitId", product.getUnit().getUnitId());
            newProductValues.put("unitId", productDto.unitId());
        }
        return new ProductChangesDto(oldProductValues, newProductValues);
    }

    public boolean isSameImage(MultipartFile newImage, Path oldImagePath) throws IOException {
        byte[] newImageBytes = newImage.getBytes();
        byte[] oldImageBytes = Files.readAllBytes(oldImagePath);

        return Arrays.equals(newImageBytes, oldImageBytes);
    }
}
