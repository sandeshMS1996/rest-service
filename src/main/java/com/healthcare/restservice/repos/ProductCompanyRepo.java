package com.healthcare.restservice.repos;

import com.healthcare.restservice.models.ProductCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCompanyRepo extends JpaRepository<ProductCompany, Long> {
}
