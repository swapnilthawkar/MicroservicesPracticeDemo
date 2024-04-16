package com.microswapdemo.inventoryservice.service;

import com.microswapdemo.inventoryservice.Dto.InventoryResponce;
import com.microswapdemo.inventoryservice.repo.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {

        this.inventoryRepository = inventoryRepository;
    }
    @Transactional(readOnly = true)
    public List<InventoryResponce> getSkuCode(List<String> skuCode){

        return inventoryRepository.findBySkuCodeIn(skuCode).
                stream().
                map(inventory ->
                                InventoryResponce.
                                        builder().
                                        skuCode(inventory.getSkuCode()).
                                        isInStock(inventory.getQuantity()>0).
                                        build()
                        ).toList();

    }
}
