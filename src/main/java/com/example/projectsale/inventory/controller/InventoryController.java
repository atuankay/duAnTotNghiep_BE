package com.example.projectsale.inventory.controller;

import com.example.projectsale.inventory.constant.InventoryConstant;
import com.example.projectsale.inventory.dto.InventoryDTO;
import com.example.projectsale.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(InventoryConstant.URI_INVENTORY)
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/list")
    public List<InventoryDTO> getAllInventories() {
        return inventoryService.getAllInventories();
    }
}
