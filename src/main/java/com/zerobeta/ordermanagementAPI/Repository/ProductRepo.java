package com.zerobeta.ordermanagementAPI.Repository;

import java.util.List;
import java.util.Optional;

import com.zerobeta.ordermanagementAPI.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);

    List<Product> findByStatus(OrderStatus status);
}
