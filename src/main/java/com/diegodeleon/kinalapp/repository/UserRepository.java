package com.diegodeleon.kinalapp.repository;

import com.diegodeleon.kinalapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}