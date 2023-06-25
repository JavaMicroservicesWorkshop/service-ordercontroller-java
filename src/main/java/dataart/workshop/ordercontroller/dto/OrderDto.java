package dataart.workshop.ordercontroller.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderDto {

    private String id;
    private List<OrderItemDto> orderItems;
    private BigDecimal totalPrice;
}
