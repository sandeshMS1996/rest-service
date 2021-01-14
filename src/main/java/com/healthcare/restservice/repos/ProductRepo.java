package com.healthcare.restservice.repos;


import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    List<Product> findProductByCategoryID(Long categoryId);

    List<Product> findProductByProductCompanyId(long companyId);

    List<Product> findProductByCategoryIDAndProductCompanyId(long category_ID, Long productCompany_id);
}
