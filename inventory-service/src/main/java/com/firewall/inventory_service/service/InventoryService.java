package com.firewall.inventory_service.service;


import com.firewall.inventory_service.dto.InventoryDto;
import com.firewall.inventory_service.repository.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;

    @Transactional(readOnly = true)
    public List<InventoryDto> isInStock(List<String> skuCode) {
        List<InventoryDto> inventoryDtos = inventoryRepo.findBySkuCodeIn(skuCode).stream().map(inventory ->
            InventoryDto.builder().skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()
        ).toList();

        return  inventoryDtos;
    }
}
