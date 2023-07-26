package com.shopping.inventoryservice.repository;

import com.shopping.inventoryservice.dto.InventoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.inventoryservice.model.Inventory;

import java.util.List;
import java.util.Optional;


public interface InventoryRepositiry extends JpaRepository<Inventory,Long> {
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
