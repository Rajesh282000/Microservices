package com.firewall.product_service.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductDto {

    private String name;
    private String description;
    private BigDecimal price;
}
