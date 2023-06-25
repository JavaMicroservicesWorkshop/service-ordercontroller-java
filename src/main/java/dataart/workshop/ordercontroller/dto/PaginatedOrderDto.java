package dataart.workshop.ordercontroller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedOrderDto {

    private List<OrderDto> data;
    private int page;
    private int totalPages;
    private int size;
}
