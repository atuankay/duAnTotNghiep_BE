package com.example.projectsale.supplier.service;

import com.example.projectsale.enums.SystemEnumStatus;
import com.example.projectsale.exception.ApiRequestException;
import com.example.projectsale.supplier.dto.SupplierDto;
import com.example.projectsale.supplier.entity.Supplier;
import com.example.projectsale.supplier.mapper.SupplierMapper;
import com.example.projectsale.supplier.repo.SupplierRepo;
import com.example.projectsale.utils.AbsServiceUtil;
import com.example.projectsale.utils.PageableUtil;
import com.example.projectsale.utils.response.Response;
import com.example.projectsale.utils.response.ResponseUtil;
import com.example.projectsale.utils.response.Responses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl extends AbsServiceUtil implements SupplierService {

    private final SupplierRepo supplierRepository;

    private final SupplierMapper supplierMapper;

    private final ResponseUtil responseUtil;

    @Override
    public ResponseEntity<Response> getAllSuppliers(String key, String isDeleted, int page, int size) {
        Pageable pageable = PageableUtil.of(page, size);
        Boolean checkIsDeleted = StringUtils.isBlank(isDeleted) ? isDeleted.equals("true") : null;
        // nếu có giá trị so sánh với True còn không trả về Null

        Page<Supplier> suppliers = supplierRepository.findByCondition(key, checkIsDeleted, pageable);
        List<SupplierDto> supplierDtoList = suppliers
                .stream()
                .map(supplierMapper::toDTO)
                .toList();

        return responseUtil.responsesSuccess("SP_001", supplierDtoList, new Responses.PageableResponse(suppliers.getTotalPages(), pageable));
    }

    @Override
    public ResponseEntity<Response> addSupplier(SupplierDto supplierDTO) {
        if (supplierRepository.existsBySupplierName(supplierDTO.getSupplierName())) {
            throw new ApiRequestException("Supplier name already exists!");
        }
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        supplier.setIsDeleted(false);
        supplier.setStatus(SystemEnumStatus.ACTIVE);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return responseUtil.responseSuccess("SP_002", savedSupplier);
    }

    @Override
    public ResponseEntity<Response> updateSupplier(UUID id, SupplierDto supplierDTO) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Supplier not found!"));
//if (StringUtils.isNotBlank(supplierDTO.getSupplierName())) {}
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setContactName(supplierDTO.getContactName());
        supplier.setContactPhone(supplierDTO.getContactPhone());
        supplier.setContactEmail(supplierDTO.getContactEmail());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setCity(supplierDTO.getCity());
        supplier.setPostalCode(supplierDTO.getPostalCode());
        supplier.setCountry(supplierDTO.getCountry());
        supplier.setWebsite(supplierDTO.getWebsite());
        supplier.setTaxId(supplierDTO.getTaxId());
        supplier.setPaymentTerms(supplierDTO.getPaymentTerms());
        supplier.setNote(supplierDTO.getNote());
        Supplier updatedSupplier = supplierRepository.save(supplier);
        return responseUtil.responseSuccess("SP_003", updatedSupplier);
    }

    @Override
    public void deleteSupplier(UUID id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Supplier not found!"));
        supplier.setIsDeleted(true);
        supplier.setStatus(SystemEnumStatus.NO_ACTIVE);
        supplierRepository.save(supplier);
    }

    @Override
    public List<SupplierDto> getAll() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers
                .stream()
                .map(supplierMapper::toDTO)
                .toList();
    }
}
