package dataart.workshop.ordercontroller.controller;

import dataart.workshop.ordercontroller.dto.*;
import dataart.workshop.ordercontroller.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
