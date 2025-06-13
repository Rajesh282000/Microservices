package com.firewall.inventory_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDto {
    private String skuCode;
    private boolean isInStock;
}
