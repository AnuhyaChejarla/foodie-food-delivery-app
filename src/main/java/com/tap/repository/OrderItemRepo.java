package com.tap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tap.model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer>{

}
