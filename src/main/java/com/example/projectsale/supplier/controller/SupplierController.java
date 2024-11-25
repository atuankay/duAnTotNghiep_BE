package com.example.projectsale.supplier.controller;

import com.example.projectsale.supplier.constant.SupplierConstant;
import com.example.projectsale.supplier.dto.SupplierDTO;
import com.example.projectsale.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(SupplierConstant.API_ADMIN_SUPPLIERS)
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping(SupplierConstant.API_GET_ALL_SUPPLIERS)
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping(SupplierConstant.API_GET_SUPPLIER_BY_ID)
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable UUID id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @PostMapping(SupplierConstant.API_ADD_SUPPLIER)
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody SupplierDTO supplierDTO) {
        return ResponseEntity.ok(supplierService.createSupplier(supplierDTO));
    }

    @PutMapping(SupplierConstant.API_UPDATE_SUPPLIER)
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable UUID id, @RequestBody SupplierDTO supplierDTO) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDTO));
    }

    @DeleteMapping(SupplierConstant.API_DELETE_SUPPLIER)
    public ResponseEntity<Void> deleteSupplier(@PathVariable UUID id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
