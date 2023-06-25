package dataart.workshop.ordercontroller.converter;

import dataart.workshop.ordercontroller.domain.Order;
import dataart.workshop.ordercontroller.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
public class OrderConverter {

    public OrderDto toOrderDto(Order order) {
        OrderDto dto = new OrderDto();

        dto.setId(order.getOrderId());
        dto.setOrderItems(order.getOrderItems());
        dto.setTotalPrice(order.getTotalPrice());

        return dto;
    }

    public PaginatedOrderDto toPaginatedDto(Page<Order> ordersPage) {
        PaginatedOrderDto dto = new PaginatedOrderDto();

        List<OrderDto> orderDtoList = ordersPage.get()
                .map(this::toOrderDto)
                .toList();

        dto.setData(orderDtoList);
        dto.setPage(ordersPage.getNumber());
        dto.setTotalPages(ordersPage.getTotalPages());
        dto.setSize(ordersPage.getNumberOfElements());

        return dto;
    }

    public Order toOrder(BookDto bookDto, UpdateOrderRequest updateOrderRequest) {
        Order order = new Order();

        Integer itemsCount = updateOrderRequest.getCount();
        order.setOrderItems(Collections.singletonList(toOrderItemDto(bookDto, itemsCount)));
        order.setTotalPrice(calculateTotalPrice(bookDto, itemsCount));

        return order;
    }

    public Order toOrder(BookDto bookDto, CreateOrderRequest createOrderRequest, String orderId) {
        Order order = new Order();

        order.setOrderId(orderId);
        Integer itemsCount = createOrderRequest.getCount();
        order.setOrderItems(Collections.singletonList(toOrderItemDto(bookDto, itemsCount)));
        order.setTotalPrice(calculateTotalPrice(bookDto, itemsCount));

        return order;
    }

    private static OrderItemDto toOrderItemDto(BookDto bookDto, Integer itensCount) {
        OrderItemDto orderItem = new OrderItemDto();

        orderItem.setBookId(bookDto.getId());
        orderItem.setQuantity(itensCount);
        orderItem.setItemPrice(bookDto.getPrice());

        return orderItem;
    }

    private static BigDecimal calculateTotalPrice(BookDto bookDto, Integer itemsCount) {
        return bookDto.getPrice().multiply(BigDecimal.valueOf(itemsCount));
    }
}
