package com.jor.repository;

import com.jor.entity.ShopBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopBillRepository extends JpaRepository<ShopBill, Long> {
    Optional<ShopBill> findTopByInvoiceNumberStartingWithOrderByInvoiceNumberDesc(String datePrefix);
}
