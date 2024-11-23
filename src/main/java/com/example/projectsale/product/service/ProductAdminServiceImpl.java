package com.example.projectsale.product.service;

import com.example.projectsale.product.dto.ProductDto;
import com.example.projectsale.product.entity.Product;
import com.example.projectsale.product.mapper.ProductDtoMapper;
import com.example.projectsale.product.repo.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductAdminServiceImpl implements ProductAdminService {
    private final ProductRepo productRepo;
    private final ProductDtoMapper dtoMapper;

    @Override
    public void addNewProduct(ProductDto productDto) {
        Product product = dtoMapper.mapToEntity(productDto);
        productRepo.save(product);
    }

    @Override
    public void updateProduct(UUID id, ProductDto productDto) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));  // Sử dụng instance productRepo
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setColor(productDto.getColor());
        product.setShortDescription(productDto.getShortDescription());
        product.setLongDescription(productDto.getLongDescription());
        productRepo.save(product);
    }

    @Override
    public void deleteProductById(UUID id) {
        productRepo.deleteById(id);
    }
}
