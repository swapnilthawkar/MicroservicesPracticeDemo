package com.microswapdemo.orderservice.service;

import com.microswapdemo.orderservice.Dto.InventoryResponce;
import com.microswapdemo.orderservice.Dto.OrderLineItemsDto;
import com.microswapdemo.orderservice.Dto.OrderRequest;
import com.microswapdemo.orderservice.model.Order;
import com.microswapdemo.orderservice.model.OrderLineItems;
import com.microswapdemo.orderservice.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public OrderService(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream().
                map(this::mapToDto).toList();
        order.setOrderLineItemList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemList().stream().
                map(OrderLineItems::getSkuCode).toList();

        //Call Inventory Service, and place order if product
        // is in stock...
        InventoryResponce[] inventoryResponces = webClient.get().
                uri("http://localhost:8082/api/inventory",uriBuilder -> uriBuilder.
                        queryParam("skuCodes",skuCodes).build()).
                retrieve().
                bodyToMono(InventoryResponce[].class).block();
        boolean allProductInStock = Arrays.stream(inventoryResponces).allMatch(InventoryResponce::isInStock);

        if(allProductInStock){
            orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Product not in stock, please try again latter...");
        }



    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
