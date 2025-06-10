package com.firewall.order_service.service;

import com.firewall.order_service.dto.OrderDto;
import com.firewall.order_service.model.Order;
import com.firewall.order_service.model.OrderLineItems;
import com.firewall.order_service.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
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

        orderRepo.save(order);
    }
}
