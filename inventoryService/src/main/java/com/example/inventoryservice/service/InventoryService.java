package com.example.inventoryservice.service;

import com.example.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
@Transactional(readOnly = true)
    public boolean isInInventory(String uniqueCode){
       return  inventoryRepository.findByUniqueCode(uniqueCode).isPresent();
    }
}