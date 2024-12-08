package com.example.projectsale.product.service;

import com.example.projectsale.category.entity.Category;
import com.example.projectsale.category.repo.CategoryRepo;
import com.example.projectsale.constant.SystemConstant;
import com.example.projectsale.enums.SystemEnumStatus;
import com.example.projectsale.imageproduct.dto.ImageProductDto;
import com.example.projectsale.imageproduct.service.ImageProductService;
import com.example.projectsale.inventory.dto.InventoryDto;
import com.example.projectsale.inventory.dto.InventorySimpleDto;
import com.example.projectsale.product.dto.ProductDto;
import com.example.projectsale.product.dto.ProductSimpleDto;
import com.example.projectsale.product.dto.request.ProductSearchDtoRequest;
import com.example.projectsale.product.dto.response.ProductDetailDtoResponse;
import com.example.projectsale.product.dto.response.ProductIndexDtoResponse;
import com.example.projectsale.product.entity.Product;
import com.example.projectsale.product.mapper.ProductDtoMapper;
import com.example.projectsale.product.repo.ProductRepo;
import com.example.projectsale.supplier.repo.SupplierRepo;
import com.example.projectsale.utils.AbsServiceUtil;
import com.example.projectsale.utils.PageableUtil;
import com.example.projectsale.utils.response.Response;
import com.example.projectsale.utils.response.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl extends AbsServiceUtil implements ProductService {

    private final ProductDtoMapper requestMapper;

    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo;

    private final ImageProductService imageProductService;

    private final ResponseUtil responseUtil;

    private final ProductDtoMapper productDtoMapper;

    private final SupplierRepo supplierRepo;

    @Override
    public Product saveProduct(ProductDto request) {

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = productRepo.save(
                Product.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .isDeleted(SystemConstant.IS_DELETED_ACTIVE)
                        .status(SystemEnumStatus.ACTIVE)
                        .shortDescription(request.getShortDescription())
                        .longDescription(request.getLongDescription())
                        .category(category)
                        .build()
        );

        if (request.getImages() != null) {
            imageProductService.createImageProduct(product, request.getImages());
        }

        return product;
    }

    @Override
    public ResponseEntity<Response> updateProduct(ProductDto productDto, UUID id) {
        Category category = categoryRepo.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setShortDescription(productDto.getShortDescription());
        product.setLongDescription(productDto.getLongDescription());

        if (!Objects.equals(product.getCategory(), category)) {
            product.setCategory(category);
        }

        return responseUtil.responseSuccess("PD_003", productDtoMapper.apply(product));
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setIsDeleted(SystemConstant.IS_DELETED_NO_ACTIVE);
        product.setStatus(SystemEnumStatus.NO_ACTIVE);
        productRepo.save(product);

    }

    @Override
    public ResponseEntity<Response> getProduct(UUID id) {

        return null;
    }

    @Override
    public ResponseEntity<Response> findAllProducts(ProductSearchDtoRequest request) {
        Pageable pageable = PageableUtil.of(request.getCurrentPage(), request.getLimitPage());
        Page<Product> all = productRepo.findAllBy(request, pageable);
        List<ProductIndexDtoResponse> list = all.stream()
                .map(productDtoMapper::toProductIndexDtoResponse)
                .toList();

        return responseUtil.responseSuccess("PD_002", list);
    }

    @Override
    public ResponseEntity<Response> findProducts(ProductSearchDtoRequest request) {
        Pageable pageable = PageableUtil.of(request.getCurrentPage(), request.getLimitPage());
        Page<Product> all = productRepo.findAllBy(request, pageable);
        List<ProductDetailDtoResponse> list = all.stream()
                .map(productDtoMapper::toProductDetailResponse)
                .toList();

        return responseUtil.responsesSuccess("PD_002", list, pageable(pageable, all.getTotalPages()));
    }

    @Override
    public ResponseEntity<Response> getProductById(UUID id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
//        productDtoMapper.toProductDetailResponse(product);
        ProductSimpleDto productSimpleDto = ProductSimpleDto.builder()
                .price(product.getPrice())
                .name(product.getName())
                .shortDescription(product.getShortDescription())
                .images(product.getImageProducts().stream()
                        .map(image -> ImageProductDto.builder()
                                .urlImage(image.getUrlImage())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        List<InventorySimpleDto> inventorySimpleDtos = product.getInventories().stream()
                .map(inventory -> InventorySimpleDto.builder()
                        .color(inventory.getColor())
                        .size(inventory.getSize())
                        .build())
                .collect(Collectors.toList());
        return responseUtil.responseSuccess("PD_004", new Object() {
            public final ProductSimpleDto products = productSimpleDto;
            public final List<InventorySimpleDto> inventories = inventorySimpleDtos;
        });
        //return responseUtil.responseSuccess("PD_004", productDtoMapper.toProductDetailResponse(product));
    }
}
