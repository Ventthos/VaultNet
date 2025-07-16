package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.domain.Change;
import com.ventthos.Vaultnet.domain.Product;
import com.ventthos.Vaultnet.dto.change.ChangeResponseDto;
import com.ventthos.Vaultnet.dto.change.CreateChangeDto;
import com.ventthos.Vaultnet.parsers.ChangeParser;
import com.ventthos.Vaultnet.repository.ChangeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChangeService {
    private final ChangeRepository changeRepository;
    private final ChangeParser changeParser;
    private final ProductService productService;

    public ChangeService(ChangeRepository changeRepository, ChangeParser changeParser, ProductService productService){
        this.changeRepository = changeRepository;
        this.changeParser = changeParser;
        this.productService = productService;
    }

    public ChangeResponseDto createChange(CreateChangeDto changeDto){
        Change change = new Change();
        change.setUser(changeDto.user());
        change.setProduct(changeDto.product());
        change.setOldValuesJson(changeDto.oldValuesJson());
        change.setNewValuesJson(changeDto.newValuesJson());

        Change savedChange =changeRepository.save(change);
        return changeParser.toChangeResponseDto(savedChange);
    }

    public List<ChangeResponseDto> getChangesFromProduct(Long productId){
        Product product = productService.getProductOrThrow(productId);
        return product.getChanges().stream().map(changeParser::toChangeResponseDto).toList();
    }
}
