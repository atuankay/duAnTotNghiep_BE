package com.example.projectsale.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductStockDto {
    private String productName;
    private Integer quantityInStock;
}
