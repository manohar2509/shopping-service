package com.shopping.inventoryservice.service;

import com.shopping.inventoryservice.dto.InventoryResponse;
import com.shopping.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.inventoryservice.repository.InventoryRepositiry;

import java.util.List;

@Service
public class InventoryService {
    
    @Autowired
    private InventoryRepositiry inventoryRepositiry;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes){
        return inventoryRepositiry.findBySkuCodeIn(skuCodes)
                .stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity()>0)
                                .build())
                .toList();
    }
}
