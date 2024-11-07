package com.backend.user.repository;

import com.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUserUUID(UUID uuid);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
