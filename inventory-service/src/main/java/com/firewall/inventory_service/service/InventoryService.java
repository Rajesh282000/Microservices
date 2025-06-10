package com.firewall.inventory_service.service;


import com.firewall.inventory_service.repository.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private InventoryRepo inventoryRepo;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepo.findBySkuCode(skuCode).isPresent();
    }
}
