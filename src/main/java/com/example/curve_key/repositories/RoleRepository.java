package com.example.curve_key.repositories;

import com.example.curve_key.entities.UserAppEntity;
import com.example.curve_key.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRoleEntity, Integer> {
}
