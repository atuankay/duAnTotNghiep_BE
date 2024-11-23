package com.example.projectsale.product.service;

import com.example.projectsale.product.dto.ProductDto;

import java.util.List;

public interface ProductPublicService {
    // Phương thức để lấy sản phẩm theo trang với các bộ lọc, sắp xếp
    List<ProductDto> getProductsByPage(int page, int size, String search, String color, Double minPrice, Double maxPrice, String sortBy, String sortOrder);
}
