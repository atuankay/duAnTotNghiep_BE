package com.example.projectsale.inventory.mapper;

import com.example.projectsale.inventory.constant.InventoryConstant;
import com.example.projectsale.inventory.dto.InventoryDTO;
import com.example.projectsale.inventory.entity.Inventory;

public class InventoryMapper {
    public static InventoryDTO toDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setProductId(inventory.getProduct().getId());
        dto.setProductName(inventory.getProduct().getName());
        dto.setQuantityInStock(inventory.getQuantityInStock());

        // Sử dụng hằng số
        if (inventory.getQuantityInStock() == 0) {
            dto.setStatusMessage(InventoryConstant.STATUS_OUT_OF_STOCK);
        } else {
            dto.setStatusMessage(InventoryConstant.STATUS_IN_STOCK);
        }
        return dto;
    }
}
