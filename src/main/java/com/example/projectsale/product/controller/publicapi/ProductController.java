package com.example.projectsale.product.controller.publicapi;

import com.example.projectsale.constant.SystemConstant;
import com.example.projectsale.product.constants.ProductConstant;
import com.example.projectsale.product.dto.request.ProductSearchDtoRequest;
import com.example.projectsale.product.service.ProductService;
import com.example.projectsale.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController(value = "publicProduct")
@RequestMapping(SystemConstant.API_PUBLIC+SystemConstant.VERSION_ONE+ ProductConstant.PRODUCT_URI)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Response> findAllProducts(@RequestBody ProductSearchDtoRequest request) {
        return productService.findAllProducts(request);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }
}
