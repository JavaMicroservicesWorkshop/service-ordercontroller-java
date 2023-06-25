package dataart.workshop.ordercontroller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderRequest {

    private String bookId;
    private Integer count;
}
