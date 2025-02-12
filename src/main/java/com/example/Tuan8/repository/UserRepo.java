package com.example.Tuan8.repository;

import com.example.Tuan8.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
