package com.ventthos.Vaultnet.repository;

import com.ventthos.Vaultnet.domain.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @NonNull
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.businesses WHERE u.userId = :userId")
    Optional<User> findByIdWithBusinesses(@Param("userId") Long userId);

    Optional<User> findByEmail(String email);
    List<User> findByEmailContainingIgnoreCase(String email);
}
