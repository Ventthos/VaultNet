package com.ventthos.Vaultnet.repository;

import com.ventthos.Vaultnet.domain.Category;
import com.ventthos.Vaultnet.domain.Unit;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    @Override
    @NonNull
    Optional<Unit> findById(@NonNull Long id);
}
