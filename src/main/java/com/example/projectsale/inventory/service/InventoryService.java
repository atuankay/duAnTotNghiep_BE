package com.example.projectsale.inventory.service;

import com.example.projectsale.inventory.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryDTO> getAllInventories();
}
