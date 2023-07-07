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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
        BookDto bookDto = getBookDtoFromBookManager(createOrderRequest.getBookId());
        Integer count = createOrderRequest.getCount();

        Order order = orderConverter.toOrder(bookDto, count);
        Order savedOrder = orderRepository.save(order);

        return new CreateOrderResponse(savedOrder.getOrderId());
    }

    public OrderDto findByOrderId(Long orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(CANT_FIND_ORDER.formatted(orderId)));

        return orderConverter.toOrderDto(order);
    }

    @Transactional
    public OrderDto update(Long orderId, UpdateOrderRequest updateOrderRequest) {
        Long bookId = updateOrderRequest.getBookId();
        BookDto bookDto = getBookDtoFromBookManager(bookId);

        Integer count = updateOrderRequest.getCount();

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(""));
        Order updatedOrder = orderConverter.toOrder(order, bookDto, count);

        return orderConverter.toOrderDto(updatedOrder);
    }

    @Transactional
    public void deleteByOrderId(Long orderId) {
        orderServiceValidator.validateOrderPresence(orderId);

        orderRepository.deleteByOrderId(orderId);
    }

    private static BookDto getBookDtoFromBookManager(Long bookId) {
        // TODO: 6/25/2023 Get bookDto from bookManager
//        ...= bookRepository.findByBookId(bookId).orElseThrow();

        //Mock
        BookDto bookDto = new BookDto();
        bookDto.setBookId(bookId);
        bookDto.setTitle("10");
        bookDto.setAuthor("100");
        bookDto.setPrice(new BigDecimal("1.00"));
        //Mock

        return bookDto;
    }
}
