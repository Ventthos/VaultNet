package com.ventthos.Vaultnet.repository;

import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.Change;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChangeRepository extends JpaRepository<Change, Long> {
    @Override
    @NonNull
    Optional<Change> findById(Long id);
}
