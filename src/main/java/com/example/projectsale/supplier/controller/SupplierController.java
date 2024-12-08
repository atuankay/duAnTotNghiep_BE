package com.example.projectsale.supplier.controller;

import com.example.projectsale.constant.SystemConstant;
import com.example.projectsale.supplier.constant.SupplierConstant;
import com.example.projectsale.supplier.dto.SupplierDto;
import com.example.projectsale.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_ONE + SupplierConstant.SUPPLIER_URI)
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> getAllSuppliers(@RequestParam("key") String key,
                                             @RequestParam(value = "isDeleted", defaultValue = "") String isDeleted,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size

    ) {
        return ResponseEntity.ok(supplierService.getAllSuppliers(key, isDeleted, page, size));
    }

    @PostMapping
    public ResponseEntity<?> addSupplier(@RequestBody SupplierDto supplierDTO) {
        return ResponseEntity.ok(supplierService.addSupplier(supplierDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(
            @PathVariable UUID id,
            @RequestBody SupplierDto supplierDTO) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable UUID id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(supplierService.getAll());
    }

}
