package com.example.projectsale.product.controller.member;

import com.example.projectsale.product.constants.ProductConstants;
import com.example.projectsale.product.dto.ProductDto;
import com.example.projectsale.product.service.ProductMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("MemberProductController")
@RequestMapping(ProductConstants.API_MEMBER_PRODUCTS)
@RequiredArgsConstructor
public class ProductMemberController {

    @GetMapping(ProductConstants.API_GET_ALL_PRODUCTS)
    public ResponseEntity<List<ProductDto>> getProductsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        List<ProductDto> products = productMemberService.getProductsByPage(page, size, search, color, minPrice, maxPrice, sortBy, sortOrder);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(ProductConstants.API_ADD_TO_CART)
    public ResponseEntity<Void> addToCart(@PathVariable UUID productId) {
        try {
            productMemberService.addProductToCart(productId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

