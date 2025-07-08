package com.ventthos.Vaultnet.repository;

import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    @Override
    @NonNull
    Optional<Business> findById(Long id);
}
