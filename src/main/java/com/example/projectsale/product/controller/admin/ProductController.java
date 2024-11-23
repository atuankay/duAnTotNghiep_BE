package com.example.projectsale.product.controller.admin;

import com.example.projectsale.product.constants.ProductConstants;
import com.example.projectsale.product.dto.ProductDto;
import com.example.projectsale.product.service.ProductAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ProductConstants.API_ADMIN_PRODUCTS)
@RequiredArgsConstructor // Tạo constructor tự động
public class ProductController {

    private final ProductAdminService productAdminService; // Inject service

    @PostMapping(ProductConstants.API_ADD_PRODUCT)
    public ResponseEntity<Void> addNewProduct(@Valid @RequestBody ProductDto product) {
        productAdminService.addNewProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(ProductConstants.API_UPDATE_PRODUCT) // Sử dụng API_UPDATE_PRODUCT từ constants
    public ResponseEntity<Void> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductDto product) {
        productAdminService.updateProduct(id, product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(ProductConstants.API_DELETE_PRODUCT) // Sử dụng API_DELETE_PRODUCT từ constants
    public ResponseEntity<Void> deleteProductById(@PathVariable UUID id) {
        productAdminService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
