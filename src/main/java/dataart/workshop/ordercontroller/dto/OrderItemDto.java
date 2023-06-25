package dataart.workshop.ordercontroller.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Embeddable
public class OrderItemDto {

    private String bookId;
    private Integer quantity;
    private BigDecimal itemPrice;
}
