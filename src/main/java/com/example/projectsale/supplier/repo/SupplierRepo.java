package com.example.projectsale.supplier.repo;

import com.example.projectsale.supplier.dto.SupplierDto;
import com.example.projectsale.supplier.entity.Supplier;
import com.example.projectsale.utils.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplierRepo extends AbstractRepository<Supplier, UUID> {

    boolean existsBySupplierName(String supplierName);

    @Query("""
                SELECT s
                FROM Supplier s
                WHERE
                    (:key IS NULL OR :key = ''
                    OR LOWER(s.supplierName) LIKE CONCAT('%', LOWER(:key), '%')
                    OR LOWER(s.contactName) LIKE CONCAT('%', LOWER(:key), '%')
                    OR LOWER(s.contactPhone) LIKE CONCAT('%', LOWER(:key), '%')
                    OR LOWER(s.address) LIKE CONCAT('%', LOWER(:key), '%')
                    OR LOWER(s.city) LIKE CONCAT('%', LOWER(:key), '%'))
                    AND
                    (:isDeleted IS NULL OR s.isDeleted = :isDeleted)
            """)
    Page<Supplier> findByCondition(@Param("key") String key, @Param("isDeleted") Boolean isDeleted, Pageable pageable);

    @Query("""
            SELECT
                s
            FROM
                Supplier s
            INNER JOIN
                Inventory iv ON iv.supplier.id = s.id
            WHERE
                iv.product.id = :productId
            """)
    Optional<Supplier> findSupplierByProductId(UUID productId);
//
//    @Query("SELECT i FROM Inventory i")
//    List<SupplierDto> findAllDTO();
}
