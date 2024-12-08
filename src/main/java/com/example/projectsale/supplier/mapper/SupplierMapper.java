package com.example.projectsale.supplier.mapper;

import com.example.projectsale.supplier.dto.SupplierDto;
import com.example.projectsale.supplier.entity.Supplier;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class SupplierMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Supplier toEntity(SupplierDto dto) {
//        return modelMapper.map(dto, Supplier.class);

        return Supplier.builder()
                .supplierName(dto.getSupplierName())
                .contactName(dto.getContactName())
                .contactPhone(dto.getContactPhone())
                .contactEmail(dto.getContactEmail())
                .address(dto.getAddress())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .website(dto.getWebsite())
                .taxId(dto.getTaxId())
                .paymentTerms(dto.getPaymentTerms())
                .note(dto.getNote())
                .build();
    }

    public SupplierDto toDTO(Supplier entity) {
        return SupplierDto.builder()
                .supplierId(entity.getId())
                .supplierName(entity.getSupplierName())
                .contactName(entity.getContactName())
                .contactPhone(entity.getContactPhone())
                .contactEmail(entity.getContactEmail())
                .address(entity.getAddress())
                .city(entity.getCity())
                .postalCode(entity.getPostalCode())
                .country(entity.getCountry())
                .website(entity.getWebsite())
                .taxId(entity.getTaxId())
                .paymentTerms(entity.getPaymentTerms())
                .note(entity.getNote())
                .build();
    }


}