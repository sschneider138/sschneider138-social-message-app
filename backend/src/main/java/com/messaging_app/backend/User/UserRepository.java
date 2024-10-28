package com.messaging_app.backend.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Page<UserResponseDto> findAllBy(Pageable pageable);

  Optional<UserResponseDto> findBy(String username);

}
