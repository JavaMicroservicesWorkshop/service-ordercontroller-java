package dataart.workshop.ordercontroller.validator;

import dataart.workshop.ordercontroller.exception.OrderNotFoundException;
import dataart.workshop.ordercontroller.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceValidator {

    private final OrderRepository orderRepository;

    public void validateOrderPresence(String bookId) {
        if (!orderRepository.existsByOrderId(bookId)) {
            throw new OrderNotFoundException("Can't find order by id: %s".formatted(bookId));
        }
    }
}
