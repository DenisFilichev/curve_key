package com.example.curve_key.repositories;

import com.example.curve_key.entities.UserAppEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAppEntity, Integer> {

    Optional<UserAppEntity> findByUserName(String username);
}
