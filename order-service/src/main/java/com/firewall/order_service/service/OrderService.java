package com.firewall.order_service.service;

import com.firewall.order_service.config.WebClientConfig;
import com.firewall.order_service.dto.InventoryDto;
import com.firewall.order_service.dto.OrderDto;
import com.firewall.order_service.model.Order;
import com.firewall.order_service.model.OrderLineItems;
import com.firewall.order_service.repository.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    private final OrderRepo orderRepo;

    private final WebClient.Builder webClientBuilder;


    public OrderService(OrderRepo orderRepo, WebClient.Builder webClientBuilder) {
        this.orderRepo = orderRepo;
        this.webClientBuilder = webClientBuilder;
    }

    public void placeOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderDto.getOrderLineItemsDtoList().stream().map(orderLineItemsDto -> {
            OrderLineItems orderLineItems = new OrderLineItems();
            orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
            orderLineItems.setPrice(orderLineItemsDto.getPrice());
            orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
            return orderLineItems;
        }).toList();

        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode).toList();

        //call inventory service, and place order if product is in stock
        InventoryDto[] inventoryDtosArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                 .retrieve()
                .bodyToMono(InventoryDto[].class)
                .block();

        boolean inStockResult = Arrays.stream(inventoryDtosArray).allMatch(InventoryDto::isInStock);

        if (inStockResult) {
            orderRepo.save(order);
        }else {
            throw new IllegalArgumentException("Product is not in stock");
        }
    }
}
