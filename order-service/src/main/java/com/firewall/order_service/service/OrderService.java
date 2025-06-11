package com.firewall.order_service.service;

import com.firewall.order_service.config.WebClientConfig;
import com.firewall.order_service.dto.OrderDto;
import com.firewall.order_service.model.Order;
import com.firewall.order_service.model.OrderLineItems;
import com.firewall.order_service.repository.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    private final OrderRepo orderRepo;

    private final WebClient webClient;


    public OrderService(OrderRepo orderRepo, WebClient webClient) {
        this.orderRepo = orderRepo;
        this.webClient = webClient;
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

        //call inventory service, and place order if product is in stock
        webClient.get()
                .uri("http://localhost:8082/api/inventory")
                 .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        orderRepo.save(order);
    }
}
