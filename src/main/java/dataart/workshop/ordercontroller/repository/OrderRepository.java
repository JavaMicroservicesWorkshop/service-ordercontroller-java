package dataart.workshop.ordercontroller.repository;

import dataart.workshop.ordercontroller.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(Long orderId);

    List<Order> findAllByBookId(Long bookId);

    boolean existsByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);

}
