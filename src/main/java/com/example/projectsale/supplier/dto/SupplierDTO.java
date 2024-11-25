package com.example.projectsale.supplier.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDTO {
    private String supplierName;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private String website;
    private String taxId;
    private String paymentTerms;
    private String note;
}
