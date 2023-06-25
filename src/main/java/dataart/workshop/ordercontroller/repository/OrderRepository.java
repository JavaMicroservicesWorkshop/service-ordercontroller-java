package dataart.workshop.ordercontroller.repository;

import dataart.workshop.ordercontroller.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(String bookId);

    boolean existsByOrderId(String orderId);

    void deleteByOrderId(String orderId);

}
