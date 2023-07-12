package dataart.workshop.ordercontroller.controller;

import dataart.workshop.ordercontroller.dto.CreateOrderRequest;
import dataart.workshop.ordercontroller.dto.CreateOrderResponse;
import dataart.workshop.ordercontroller.dto.OrderDto;
import dataart.workshop.ordercontroller.dto.PaginatedOrderDto;
import dataart.workshop.ordercontroller.dto.UpdateOrderRequest;
import dataart.workshop.ordercontroller.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public PaginatedOrderDto getAll(@RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer size) {
        return orderService.findAll(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrderResponse(createOrderRequest);
    }

    @GetMapping("/{orderId}")
    public OrderDto getByOrderId(@PathVariable Long orderId) {
        return orderService.findByOrderId(orderId);
    }

    @GetMapping("/book/{bookId}")
    public List<OrderDto> getAllOrdersByBookId(@PathVariable Long bookId) {
        return orderService.findAllByBookId(bookId);
    }

    @PutMapping("/{orderId}")
    public OrderDto update(@PathVariable Long orderId,
                           @RequestBody @Valid UpdateOrderRequest updateOrderRequest) {
        return orderService.update(orderId, updateOrderRequest);
    }

    @DeleteMapping("/{orderId}")
    public void delete(@PathVariable Long orderId) {
        orderService.deleteByOrderId(orderId);
    }
}
