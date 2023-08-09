package com.example.curve_key.security;

import com.example.curve_key.entities.UserAppEntity;
import com.example.curve_key.entities.UserRoleEntity;
import com.example.curve_key.repositories.RoleRepository;
import com.example.curve_key.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AppUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDetails> findAll(){
        List<UserDetails> list = userRepository.findAll().stream().map(e -> AppUserDetails
                .builder()
                .id(e.getId())
                .username(e.getUserName())
                .password("{noop}" + e.getPassword())
                .isAccountNonLocked(e.isActive())
                .roles(e.getRoles().stream().map(UserRoleEntity::getRoleName).collect(Collectors.toSet()))
                .build()
        ).collect(Collectors.toList());
        list.forEach(System.out::println);
        return list;
    }
}
