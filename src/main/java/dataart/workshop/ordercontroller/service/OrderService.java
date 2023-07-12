package dataart.workshop.ordercontroller.service;

import dataart.workshop.ordercontroller.converter.OrderConverter;
import dataart.workshop.ordercontroller.domain.Order;
import dataart.workshop.ordercontroller.dto.BookDto;
import dataart.workshop.ordercontroller.dto.CreateOrderRequest;
import dataart.workshop.ordercontroller.dto.CreateOrderResponse;
import dataart.workshop.ordercontroller.dto.OrderDto;
import dataart.workshop.ordercontroller.dto.PaginatedOrderDto;
import dataart.workshop.ordercontroller.dto.UpdateOrderRequest;
import dataart.workshop.ordercontroller.exception.OrderNotFoundException;
import dataart.workshop.ordercontroller.repository.OrderRepository;
import dataart.workshop.ordercontroller.utils.PageUtils;
import dataart.workshop.ordercontroller.validator.OrderServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final String CANT_FIND_ORDER = "Can't find order by id: %s";
    private static final String BOOK_MANAGER_URL = "http://localhost:8082/api/v1/books/";

    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final PageUtils pageUtils;
    private final OrderServiceValidator orderServiceValidator;
    private final BookService bookService;

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

    public List<OrderDto> findAllByBookId(Long bookId) {
        List<Order> orders = orderRepository.findAllByBookId(bookId);

        return orders.stream()
                .map(orderConverter::toOrderDto)
                .collect(Collectors.toList());
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

    private BookDto getBookDtoFromBookManager(Long bookId) {
        return bookService.getBookDtoFromBookManager(bookId);
    }
}
