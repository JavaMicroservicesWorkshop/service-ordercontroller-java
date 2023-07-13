package dataart.workshop.ordercontroller.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDto {

    private Long orderId;
    private String bookName;
    private Integer quantity;
    private BigDecimal totalPrice;
}
