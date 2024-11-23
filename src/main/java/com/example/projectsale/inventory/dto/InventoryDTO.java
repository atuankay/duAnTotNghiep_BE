package com.example.projectsale.inventory.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InventoryDTO {
    private UUID productId;
    private String productName;
    private Integer quantityInStock;
    private String statusMessage;
}
