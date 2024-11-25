package com.example.projectsale.supplier.mapper;

import com.example.projectsale.supplier.dto.SupplierDTO;
import com.example.projectsale.supplier.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierDTOMapper {

    // Convert Entity to DTO
    public SupplierDTO toDTO(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        return SupplierDTO.builder()
                .supplierName(supplier.getSupplierName())
                .contactName(supplier.getContactName())
                .contactPhone(supplier.getContactPhone())
                .contactEmail(supplier.getContactEmail())
                .address(supplier.getAddress())
                .city(supplier.getCity())
                .postalCode(supplier.getPostalCode())
                .country(supplier.getCountry())
                .website(supplier.getWebsite())
                .taxId(supplier.getTaxId())
                .paymentTerms(supplier.getPaymentTerms())
                .note(supplier.getNote())
                .build();
    }

    // Convert DTO to Entity
    public Supplier toEntity(SupplierDTO supplierDTO) {
        if (supplierDTO == null) {
            return null;
        }
        return Supplier.builder()
                .supplierName(supplierDTO.getSupplierName())
                .contactName(supplierDTO.getContactName())
                .contactPhone(supplierDTO.getContactPhone())
                .contactEmail(supplierDTO.getContactEmail())
                .address(supplierDTO.getAddress())
                .city(supplierDTO.getCity())
                .postalCode(supplierDTO.getPostalCode())
                .country(supplierDTO.getCountry())
                .website(supplierDTO.getWebsite())
                .taxId(supplierDTO.getTaxId())
                .paymentTerms(supplierDTO.getPaymentTerms())
                .note(supplierDTO.getNote())
                .build();
    }
}
