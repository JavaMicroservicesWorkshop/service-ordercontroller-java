package dataart.workshop.ordercontroller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class BookOrdersDto {

    private List<OrderDto> orderDtos;
}
