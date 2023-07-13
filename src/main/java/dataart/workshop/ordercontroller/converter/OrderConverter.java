package dataart.workshop.ordercontroller.converter;

import dataart.workshop.ordercontroller.domain.Order;
import dataart.workshop.ordercontroller.dto.BookDto;
import dataart.workshop.ordercontroller.dto.OrderDto;
import dataart.workshop.ordercontroller.dto.PaginatedOrderDto;
import dataart.workshop.ordercontroller.service.BookService;
import dataart.workshop.ordercontroller.utils.OrderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final BookService bookService;

    public OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setOrderId(order.getOrderId());

        BookDto bookDto = bookService.getBookDtoFromBookManager(order.getBookId());
        orderDto.setBookName(OrderUtils.getBeautifiedBookName(bookDto));
        orderDto.setQuantity(order.getQuantity());
        orderDto.setTotalPrice(order.getTotalPrice());

        return orderDto;
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

    public Order toOrder(Order order, BookDto bookDto, Integer itemsCount) {
        order.setBookId(bookDto.getBookId());
        order.setQuantity(itemsCount);
        order.setTotalPrice(calculateTotalPrice(bookDto, itemsCount));

        return order;
    }

    public Order toOrder(BookDto bookDto, Integer itemsCount) {
        Order order = new Order();

        order.setBookId(bookDto.getBookId());
        order.setQuantity(itemsCount);
        order.setTotalPrice(calculateTotalPrice(bookDto, itemsCount));

        return order;
    }

    private static BigDecimal calculateTotalPrice(BookDto bookDto, Integer itemsCount) {
        return bookDto.getPrice().multiply(BigDecimal.valueOf(itemsCount));
    }
}
