package com.example.projectsale.product.dto;

import com.example.projectsale.imageproduct.dto.ImageProductDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductSimpleDto {
    private String name;
    private String shortDescription;
    private List<ImageProductDto> images;
    private BigDecimal price;
}
