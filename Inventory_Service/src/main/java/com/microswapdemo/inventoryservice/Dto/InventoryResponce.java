package com.microswapdemo.inventoryservice.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponce {
     private String skuCode;
     private boolean isInStock;
}
