package com.example.curve_key.repositories;

import com.example.curve_key.entities.VpnClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VpnClientRepository extends JpaRepository<VpnClientEntity, Integer> {

}
