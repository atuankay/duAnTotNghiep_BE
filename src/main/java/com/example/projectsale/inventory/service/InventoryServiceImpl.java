package com.example.projectsale.inventory.service;

import com.example.projectsale.inventory.dto.InventoryDTO;
import com.example.projectsale.inventory.mapper.InventoryMapper;
import com.example.projectsale.inventory.repo.InventoryRepo;
import com.example.projectsale.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepo inventoryRepository;

    @Override
    public List<InventoryDTO> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(InventoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
