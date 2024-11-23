package com.example.projectsale.product.service;

import com.example.projectsale.product.dto.ProductDto;
import com.example.projectsale.product.entity.Product;

import java.util.UUID;

public interface ProductAdminService {
    void addNewProduct(ProductDto product);
    void updateProduct(UUID id, ProductDto product);
    void deleteProductById(UUID id);
}
