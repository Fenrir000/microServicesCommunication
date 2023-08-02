package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
@Transactional(readOnly = true)
    public List<InventoryResponse> isInInventory(List<String> uniqueCode){
       return  inventoryRepository.findByUniqueCodeIn(uniqueCode).stream().map(inventory ->
           InventoryResponse.builder().uniqueCode(inventory.getUniqueCode()).isInInventory(inventory.getQuantity()>0).build()
       ).toList();
    }
}
