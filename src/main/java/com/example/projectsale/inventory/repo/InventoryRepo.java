package com.example.projectsale.inventory.repo;

import com.example.projectsale.inventory.entity.Inventory;
import com.example.projectsale.utils.AbstractRepository;

import java.util.UUID;

public interface InventoryRepo extends AbstractRepository<Inventory, UUID> {
}
