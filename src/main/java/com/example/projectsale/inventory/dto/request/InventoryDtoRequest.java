package com.example.projectsale.inventory.dto.request;

import com.example.projectsale.enums.StatusInventory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class InventoryDtoRequest {
    private UUID categoryId;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    private String shortDescription;

    private String longDescription;

    private MultipartFile[] images;

    private String color;

    private UUID supplierId;

    @Positive
    private Integer quantityInStock;

    @Positive
    private Integer minimumInStock;

    @Positive
    private Integer maximumInStock;

    private Date lastRestockDate;

    private StatusInventory statusInventory;

    @NotBlank
    private String size;

}
