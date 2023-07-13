package dataart.workshop.ordercontroller.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookDto {

    private Long bookId;
    private String title;
    private String author;
    private BigDecimal price;
}
