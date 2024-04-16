package com.microswapdemo.orderservice.repo;

import com.microswapdemo.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
