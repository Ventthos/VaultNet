package com.ventthos.Vaultnet.repository;

import com.ventthos.Vaultnet.domain.Category;
import com.ventthos.Vaultnet.domain.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    @NonNull
    Optional<Category> findById(@NonNull Long id);
}
