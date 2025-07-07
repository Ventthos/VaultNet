package com.ventthos.Vaultnet.repository;

import com.ventthos.Vaultnet.domain.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @NonNull
    Optional<User> findById(@NonNull Long id);
    Optional<User> findByEmail(String email);
}
