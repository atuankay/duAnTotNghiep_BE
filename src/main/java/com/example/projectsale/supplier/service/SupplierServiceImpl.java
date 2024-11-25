package com.example.projectsale.supplier.service;

import com.example.projectsale.supplier.dto.SupplierDTO;
import com.example.projectsale.supplier.entity.Supplier;
import com.example.projectsale.supplier.mapper.SupplierDTOMapper;
import com.example.projectsale.supplier.repo.SupplierRepo;
import com.example.projectsale.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepo supplierRepo;
    private final SupplierDTOMapper supplierDTOMapper;

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepo.findAll().stream()
                .map(supplierDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDTO getSupplierById(UUID id) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found!"));
        return supplierDTOMapper.toDTO(supplier);
    }

    @Override
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = supplierDTOMapper.toEntity(supplierDTO);
        supplier = supplierRepo.save(supplier);
        return supplierDTOMapper.toDTO(supplier);
    }

    @Override
    public SupplierDTO updateSupplier(UUID id, SupplierDTO supplierDTO) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found!"));
        Supplier updatedSupplier = supplierDTOMapper.toEntity(supplierDTO);
        updatedSupplier.setId(supplier.getId());
        supplierRepo.save(updatedSupplier);
        return supplierDTOMapper.toDTO(updatedSupplier);
    }

    @Override
    public void deleteSupplier(UUID id) {
        if (!supplierRepo.existsById(id)) {
            throw new RuntimeException("Supplier not found!");
        }
        supplierRepo.deleteById(id);
    }
}
