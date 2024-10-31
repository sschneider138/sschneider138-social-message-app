package com.messaging.app.backend.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllPaginatedUsers(Pageable pageable);

    User findByUsername(String username);

}
