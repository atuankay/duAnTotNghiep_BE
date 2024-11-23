package com.example.projectsale.product.mapper;

import com.example.projectsale.product.dto.ProductDto;
import com.example.projectsale.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {
    public ProductDto apply(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .color(product.getColor())
                .shortDescription(product.getShortDescription())
                .longDescription(product.getLongDescription())
                .build();
    }

    public Product mapToEntity(ProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .color(dto.getColor())
                .shortDescription(dto.getShortDescription())
                .longDescription(dto.getLongDescription())
                .build();
    }
}
