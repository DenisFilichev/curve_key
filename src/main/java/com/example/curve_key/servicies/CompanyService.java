package com.example.curve_key.servicies;

import com.example.curve_key.entities.CompanyEntity;
import com.example.curve_key.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Set<CompanyEntity> findFullAll(){
        return new HashSet<>(companyRepository.findFullCompanies());
    }

    public CompanyEntity save(CompanyEntity company){
        return companyRepository.save(company);
    }
}
