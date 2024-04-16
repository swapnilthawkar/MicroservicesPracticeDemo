package com.microswapdemo.inventoryservice.controller;

import com.microswapdemo.inventoryservice.Dto.InventoryResponce;
import com.microswapdemo.inventoryservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {

        this.inventoryService = inventoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponce>   inStock(@RequestParam List<String> skuCode){

        return inventoryService.getSkuCode(skuCode);
    }
}
