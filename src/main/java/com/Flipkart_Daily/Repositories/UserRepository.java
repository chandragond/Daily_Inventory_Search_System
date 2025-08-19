package com.Flipkart_Daily.Repositories;

import com.Flipkart_Daily.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByMobile(String mobile);
}
