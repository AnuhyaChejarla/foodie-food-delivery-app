
package com.tap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tap.model.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> findByUserUserId(int userId);

}
