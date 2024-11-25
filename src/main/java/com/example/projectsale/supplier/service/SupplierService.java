package com.example.projectsale.supplier.service;

import com.example.projectsale.supplier.dto.SupplierDTO;

import java.util.List;
import java.util.UUID;

public interface SupplierService {
    List<SupplierDTO> getAllSuppliers();

    SupplierDTO getSupplierById(UUID id);

    SupplierDTO createSupplier(SupplierDTO supplierDTO);

    SupplierDTO updateSupplier(UUID id, SupplierDTO supplierDTO);

    void deleteSupplier(UUID id);
}
