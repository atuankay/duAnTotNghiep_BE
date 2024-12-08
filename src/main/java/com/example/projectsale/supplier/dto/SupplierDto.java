package com.example.projectsale.supplier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {

    private UUID supplierId;

    @NotBlank
    private String supplierName;

    @NotBlank
    private String contactName;

    @NotBlank
//    @Pattern(regexp = "^(\\+84|0)[3-9]\\d{8}$",
//            message = "Số điện thoại không hợp lệ")
    private String contactPhone;

    @NotBlank
//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
//            message = "Email không hợp lệ")
    private String contactEmail;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String country;

    @NotBlank
    private String website;

    @NotBlank
    private String taxId;

    @NotBlank
    private String paymentTerms;


    private String note;
}
