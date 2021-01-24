package com.healthcare.restservice.repos;

import com.healthcare.restservice.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Long> {

    List<Purchase> findAllByUsername(String username);
}
