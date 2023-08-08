package com.example.curve_key.repositories;

import com.example.curve_key.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CompanyRepository extends JpaRepository<CompanyEntity,Integer> {

    @Query(name = "Host.findAllWithCompanyAndClientAndServer",
            value = "SELECT company FROM CompanyEntity company " +
                    "LEFT JOIN FETCH company.clients as client LEFT JOIN FETCH client.vpnServer")
    Set<CompanyEntity> findFullCompanies();
}
