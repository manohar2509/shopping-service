package com.shopping.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.inventoryservice.repository.InventoryRepositiry;

@Service
public class InventoryService {
    
    @Autowired
    private InventoryRepositiry inventoryRepositiry;

    @Transactional(readOnly = true)
    public boolean inInStock(String skuCode){
        return inventoryRepositiry.findBySkuCode(skuCode).isPresent();
    }
}
