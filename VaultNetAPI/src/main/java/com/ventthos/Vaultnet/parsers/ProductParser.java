package com.ventthos.Vaultnet.parsers;

import com.ventthos.Vaultnet.domain.Product;
import com.ventthos.Vaultnet.dto.products.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductParser {
    private final CategoryParser categoryParser;
    private final UnitParser unitParser;

    public ProductParser(CategoryParser categoryParser, UnitParser unitParser){
        this.categoryParser = categoryParser;
        this.unitParser = unitParser;
    }

    public ProductResponseDto toProductResponseDto(Product product){
        return new ProductResponseDto(
                product.getProductId(),
                product.getBusiness().getBusinessId(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getQuantity(),
                unitParser.toUnitResponseDto(product.getUnit().getUnitId(), product.getUnit()),
                categoryParser.toCategoryDto(product.getCategory().getCategoryId(), product.getCategory())
        );
    }
}
