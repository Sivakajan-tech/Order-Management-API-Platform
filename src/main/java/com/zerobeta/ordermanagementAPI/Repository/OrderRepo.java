package com.zerobeta.ordermanagementAPI.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.zerobeta.ordermanagementAPI.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {
    Optional<Order> findByOrderId(UUID id);

    List<Order> findByStatus(OrderStatus status);
}
