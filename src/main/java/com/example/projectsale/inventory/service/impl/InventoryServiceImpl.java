package com.example.projectsale.inventory.service.impl;

import com.example.projectsale.constant.SystemConstant;
import com.example.projectsale.enums.StatusInventory;
import com.example.projectsale.enums.SystemEnumStatus;
import com.example.projectsale.inventory.dto.request.InventoryDtoRequest;
import com.example.projectsale.inventory.dto.request.InventorySearchDtoRequest;
import com.example.projectsale.inventory.dto.response.InventoryIndexDtoResponse;
import com.example.projectsale.inventory.entity.Inventory;
import com.example.projectsale.inventory.mapper.InventoryDtoMapper;
import com.example.projectsale.inventory.repo.InventoryRepo;
import com.example.projectsale.inventory.service.InventoryService;
import com.example.projectsale.product.dto.ProductDto;
import com.example.projectsale.product.entity.Product;
import com.example.projectsale.product.repo.ProductRepo;
import com.example.projectsale.product.service.ProductService;
import com.example.projectsale.supplier.entity.Supplier;
import com.example.projectsale.supplier.repo.SupplierRepo;
import com.example.projectsale.utils.AbsServiceUtil;
import com.example.projectsale.utils.PageableUtil;
import com.example.projectsale.utils.response.Response;
import com.example.projectsale.utils.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class    InventoryServiceImpl extends AbsServiceUtil implements InventoryService {

    private final InventoryRepo inventoryRepo;
    private final ProductRepo productRepo;
    private final ResponseUtil responseUtil;
    private final SupplierRepo supplierRepo;
    private final InventoryDtoMapper inventoryDtoMapper;
    private final ProductService productService;


    @Override
    public ResponseEntity<Response> createInventory(InventoryDtoRequest request) {
        if (inventoryRepo.existsBySizeAndColorAndProductName(request.getSize(),
                request.getColor(), request.getName())) {
            throw new RuntimeException("Product exists");
        }

        if (inventoryRepo.existsById(request.getSupplierId())) {
            throw new RuntimeException("Supplier exists");
        }

        Supplier supplier = supplierRepo.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Product product = productService.saveProduct(ProductDto.builder()
                .name(request.getName())
                .categoryId(request.getCategoryId())
                .price(request.getPrice())
                .shortDescription(request.getShortDescription())
                .shortDescription(request.getShortDescription())
                .images(request.getImages())
                .build());

        Inventory inventory = inventoryRepo.save(
                Inventory.builder()
                        .product(product)
                        .size(request.getSize())
                        .quantityInStock(request.getQuantityInStock())
                        .maximumInStock(request.getMaximumInStock())
                        .minimumInStock(request.getMinimumInStock())
                        .statusInventory(request.getStatusInventory())
                        .color(request.getColor())
                        .lastRestockDate(new Date())
                        .supplier(supplier)
                        .status(SystemEnumStatus.ACTIVE)
                        .isDeleted(SystemConstant.IS_DELETED_ACTIVE)
                        .statusInventory(StatusInventory.NEW)
                        .build()
        );

        return responseUtil.responseSuccess("IV_001", inventoryDtoMapper.apply(inventory));
    }

    @Override
    public ResponseEntity<Response> updateInventory(InventoryDtoRequest request, UUID id) {
        if (inventoryRepo.existsBySizeAndColorAndProductName(request.getSize(),
                request.getColor(), request.getName())) {
            throw new RuntimeException("Product exists");
        }

        Supplier supplier = supplierRepo.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        inventory.setQuantityInStock(inventory.getQuantityInStock() + request.getQuantityInStock());
        inventory.setMaximumInStock(request.getMaximumInStock());
        inventory.setMinimumInStock(request.getMinimumInStock());
        inventory.setStatusInventory(request.getStatusInventory());
        inventory.setLastRestockDate(new Date(System.currentTimeMillis()));
        inventory.setSize(request.getSize());
        inventory.setColor(request.getColor());
        inventory.setSupplier(supplier);

        return responseUtil.responseSuccess("IV_003", inventoryDtoMapper.apply(inventory));
    }

    @Override
    public ResponseEntity<Response> deleteInventory(UUID id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setIsDeleted(SystemConstant.IS_DELETED_NO_ACTIVE);
        product.setStatus(SystemEnumStatus.NO_ACTIVE);

        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventory.setStatus(SystemEnumStatus.NO_ACTIVE);
        inventory.setStatusInventory(StatusInventory.DISCONTINUED);
        inventory.setIsDeleted(SystemConstant.IS_DELETED_NO_ACTIVE);
        productRepo.save(product);

        return responseUtil.responseSuccess("IV_004", inventory.getId());
    }


    @Override
    public ResponseEntity<Response> getAllInventory(InventorySearchDtoRequest request) {
        Pageable pageable = PageableUtil.of(request.getCurrentPage(), request.getLimitPage());
        Page<Inventory> all = inventoryRepo.findAllBy(request, pageable);
        List<InventoryIndexDtoResponse> list = all.stream()
                .map(inventoryDtoMapper::toInventoryIndexDtoResponse)
                .toList();
        return responseUtil.responsesSuccess("IV_002", list, pageable(pageable, all.getTotalPages()));
    }

    @Override
    public void updateInventory(UUID productId, String size, String color, Integer quantity) {
        Inventory inventory = inventoryRepo.findByInventory(productId, size, color)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        if (inventory.getQuantityInStock() < quantity) {
            throw new RuntimeException("Quantity out of stock");
        }
        inventory.setQuantityInStock(inventory.getQuantityInStock() - quantity);
        inventoryRepo.save(inventory);
    }

}
