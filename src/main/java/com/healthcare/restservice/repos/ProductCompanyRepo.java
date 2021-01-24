package com.healthcare.restservice.repos;

import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.models.ProductCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCompanyRepo extends JpaRepository<ProductCompany, Long> {
    List<ProductCompany> findProductCompaniesByCategories(Category category);
}
