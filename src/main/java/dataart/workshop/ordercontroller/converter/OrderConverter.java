package dataart.workshop.ordercontroller.converter;

import dataart.workshop.ordercontroller.domain.Order;
import dataart.workshop.ordercontroller.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class OrderConverter {

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
