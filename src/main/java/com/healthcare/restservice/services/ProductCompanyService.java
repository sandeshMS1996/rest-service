package com.healthcare.restservice.services;

import com.healthcare.restservice.models.ProductCompany;
import com.healthcare.restservice.repos.ProductCompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCompanyService {

    @Autowired
    private ProductCompanyRepo productCompanyRepo;

    public ProductCompany addProductCompany(ProductCompany productCompany) {
        return this.productCompanyRepo.save(productCompany);
    }

    public List<ProductCompany> getAllCompanies() {
        return this.productCompanyRepo.findAll();
    }


}
