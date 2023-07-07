package dataart.workshop.ordercontroller.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @Column
    private Long bookId;

    @Column
    private Integer quantity;

    @Column
    private BigDecimal totalPrice;
}
