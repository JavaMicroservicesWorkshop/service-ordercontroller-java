package dataart.workshop.ordercontroller.domain;

import dataart.workshop.ordercontroller.dto.OrderItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String orderId;

    @ElementCollection
    private List<OrderItemDto> orderItems;

    @Column
    private BigDecimal totalPrice;
}
