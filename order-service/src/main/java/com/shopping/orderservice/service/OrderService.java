package com.shopping.orderservice.service;

import java.util.Arrays;
import java.util.UUID;
import java.util.List;

import com.shopping.orderservice.dto.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.orderservice.dto.OrderLineItemsDto;
import com.shopping.orderservice.dto.OrderRequest;
import com.shopping.orderservice.model.Order;
import com.shopping.orderservice.model.OrderLineItems;
import com.shopping.orderservice.repository.OrderRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient webClient;
    public OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderRequest.getOrderLineItemsDtoList()
            .stream()
            .map(this::mapToDto)
            .toList());
        List<String> skuCodes = order.getOrderLineItemsList()
                        .stream()
                                .map(OrderLineItems::getSkuCode)
                                        .toList();
        InventoryResponse[] inventoryResponses = webClient.get()
                        .uri("http://localhost:8082/api/inventory",
                                uriBuilder->uriBuilder.queryParam("skuCode",skuCodes).build())
                                .retrieve()
                                        .bodyToMono(InventoryResponse[].class)
                                                .block();
        boolean allMatches = Arrays.stream(inventoryResponses)
                        .allMatch(inventoryResponse -> inventoryResponse.getIsInStock());
        if(allMatches)
            orderRepository.save(order);
        else
            throw new IllegalArgumentException("Stock is not available, please try later");
    }

    
    
}
