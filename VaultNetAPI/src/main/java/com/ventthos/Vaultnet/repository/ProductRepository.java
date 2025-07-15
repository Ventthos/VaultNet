package com.ventthos.Vaultnet.repository;

import com.ventthos.Vaultnet.domain.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    @NonNull
        Optional<Product> findById(@NonNull Long id);
}
