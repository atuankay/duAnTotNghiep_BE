package com.example.projectsale.supplier.service;

import com.example.projectsale.supplier.dto.SupplierDto;
import com.example.projectsale.supplier.entity.Supplier;
import com.example.projectsale.utils.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SupplierService {

    ResponseEntity<Response> getAllSuppliers(String key, String isDeleted,int page, int size);
    ResponseEntity<Response> addSupplier(SupplierDto supplierDTO);
    ResponseEntity<Response> updateSupplier(UUID id, SupplierDto supplierDTO);
    void deleteSupplier(UUID id);
    List<SupplierDto> getAll();

}
