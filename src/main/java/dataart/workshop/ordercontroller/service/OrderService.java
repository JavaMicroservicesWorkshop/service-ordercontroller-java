package dataart.workshop.ordercontroller.service;

import dataart.workshop.ordercontroller.converter.OrderConverter;
import dataart.workshop.ordercontroller.domain.Order;
import dataart.workshop.ordercontroller.dto.*;
import dataart.workshop.ordercontroller.exception.OrderNotFoundException;
import dataart.workshop.ordercontroller.repository.OrderRepository;
import dataart.workshop.ordercontroller.utils.PageUtils;
import dataart.workshop.ordercontroller.validator.OrderServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final String CANT_FIND_ORDER = "Can't find order by id: %s";

    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final PageUtils pageUtils;
    private final OrderServiceValidator orderServiceValidator;

    public PaginatedOrderDto findAll(Integer page, Integer size) {
        Pageable pageable = pageUtils.adjustPageable(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageable);

        return orderConverter.toPaginatedDto(orderPage);
    }

    public CreateOrderResponse createOrderResponse(CreateOrderRequest createOrderRequest) {
        String orderId = UUID.randomUUID().toString();

        BookDto bookDto = getBookDtoFromBookManager(createOrderRequest.getBookId());
        Order order = orderConverter.toOrder(bookDto, createOrderRequest, orderId);
        orderRepository.save(order);

        return new CreateOrderResponse(orderId);
    }

    public OrderDto findByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(CANT_FIND_ORDER.formatted(orderId)));

        return orderConverter.toOrderDto(order);
    }

    public OrderDto update(String orderId, UpdateOrderRequest updateOrderRequest) {
        Order existingOrder = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(""));

        String bookId = updateOrderRequest.getBookId();
        Order updateOrder = orderConverter.toOrder(getBookDtoFromBookManager(bookId), updateOrderRequest);
        update(existingOrder, updateOrder);

        return orderConverter.toOrderDto(existingOrder);
    }

    public void deleteByOrderId(String orderId) {
        orderServiceValidator.validateOrderPresence(orderId);

        orderRepository.deleteByOrderId(orderId);
    }

    private void update(Order destination, Order source) {
        destination.setOrderItems(source.getOrderItems());
        destination.setTotalPrice(source.getTotalPrice());
    }

    private static BookDto getBookDtoFromBookManager(String bookId) {
        // TODO: 6/25/2023 Get bookDto from bookManager
//        ...= bookRepository.findByBookId(bookId).orElseThrow();
        return new BookDto();
    }
}
