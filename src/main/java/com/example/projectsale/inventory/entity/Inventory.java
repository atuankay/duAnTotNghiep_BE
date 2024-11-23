package com.example.projectsale.inventory.entity;

import com.example.projectsale.enums.StatusInventory;
import com.example.projectsale.product.entity.Product;
import com.example.projectsale.supplier.entity.Supplier;
import com.example.projectsale.utils.base.BaseEntityUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory extends BaseEntityUtil {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    @Column(name = "minimum_in_stock")
    private Integer minimumInStock;

    @Column(name = "maximum_in_stock")
    private Integer maximumInStock;

    @Column(name = "last_restock_date")
    private Date lastRestockDate;

    @Column(name = "status_inventory")
    @Enumerated(EnumType.STRING)
    private StatusInventory statusInventory;

    @Column(name = "size")
    private String size;
}
