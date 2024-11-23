package com.example.projectsale.category.repo;

import com.example.projectsale.category.dto.ProductStockDto;
import com.example.projectsale.category.entity.Category;
import com.example.projectsale.enums.SystemEnumStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {

    boolean existsCategoryByName(String name);

    Page<Category> findCategoriesByStatus(SystemEnumStatus status, Pageable pageable);

    @Query("SELECT new com.example.projectsale.category.dto.ProductStockDto(p.name, i.quantityInStock) " +
            "FROM Category c " +
            "JOIN c.products p " +
            "JOIN Inventory i ON i.product.id = p.id " +
            "WHERE c.id = :categoryId")
    List<ProductStockDto> findCategoryWithProductStock(UUID categoryId);
}
