package dataart.workshop.ordercontroller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

    private Long bookId;
    private Integer count;
}
