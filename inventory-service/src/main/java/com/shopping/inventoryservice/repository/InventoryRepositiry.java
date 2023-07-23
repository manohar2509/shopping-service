package com.shopping.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.inventoryservice.model.Inventory;
import java.util.Optional;


public interface InventoryRepositiry extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findBySkuCode(String skuCode);
}
