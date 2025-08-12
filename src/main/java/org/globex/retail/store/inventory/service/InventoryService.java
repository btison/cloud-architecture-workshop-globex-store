package org.globex.retail.store.inventory.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.globex.retail.store.inventory.model.dto.InventoryDto;
import org.globex.retail.store.inventory.model.dto.InventoryMapper;
import org.globex.retail.store.inventory.model.entity.Inventory;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class InventoryService {

    public List<InventoryDto> findAll() {
        return Inventory.listAll().stream().map(i -> InventoryMapper.toDto((Inventory) i)).collect(Collectors.toList());
    }

    public List<InventoryDto> findByItemId(String itemId) {
        return Inventory.findByItemId(itemId).stream().map(InventoryMapper::toDto).collect(Collectors.toList());
    }

}
