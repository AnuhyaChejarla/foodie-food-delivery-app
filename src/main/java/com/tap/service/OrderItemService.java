package com.tap.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tap.dto.OrderItemDTO;
import com.tap.dto.OrderItemRequestDTO;
import com.tap.model.Menu;
import com.tap.model.Order;
import com.tap.model.OrderItem;
import com.tap.repository.MenuRepo;
import com.tap.repository.OrderItemRepo;
import com.tap.repository.OrderRepo;

@Service
public class OrderItemService {

	private OrderItemRepo orderItemRepo;
	private OrderRepo orderRepo;
	private MenuRepo menuRepo;

	public OrderItemService(OrderItemRepo orderItemRepo,
			OrderRepo orderRepo,
			MenuRepo menuRepo) {
		this.orderItemRepo = orderItemRepo;
		this.orderRepo = orderRepo;
		this.menuRepo = menuRepo;
	}

	public List<OrderItemDTO> getOrderItems() {

	    return orderItemRepo.findAll()
	            .stream()
	            .map(orderItem -> new OrderItemDTO(
	                    orderItem.getOrderItemId(),
	                    orderItem.getQuantity(),
	                    orderItem.getPrice(),
	                    orderItem.getOrder().getOrderId(),
	                    orderItem.getMenu().getMenuId()
	            ))
	            .toList();
	}
	
	public OrderItemDTO getOrderItemById(int orderItemId) {

	    OrderItem orderItem = orderItemRepo.findById(orderItemId)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND,
	                    "OrderItem not found with id: " + orderItemId
	            ));

	    return new OrderItemDTO(
	            orderItem.getOrderItemId(),
	            orderItem.getQuantity(),
	            orderItem.getPrice(),
	            orderItem.getOrder().getOrderId(),
	            orderItem.getMenu().getMenuId()
	    );
	}
	public OrderItemDTO addOrderItem(OrderItemRequestDTO orderItemDTO) {

	    Order order = orderRepo.findById(orderItemDTO.getOrderId())
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND,
	                    "Order not found with id: " + orderItemDTO.getOrderId()
	            ));

	    Menu menu = menuRepo.findById(orderItemDTO.getMenuId())
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND,
	                    "Menu not found with id: " + orderItemDTO.getMenuId()
	            ));

	    OrderItem orderItem = new OrderItem();

	    orderItem.setQuantity(orderItemDTO.getQuantity());
	    orderItem.setPrice(orderItemDTO.getPrice());
	    orderItem.setOrder(order);
	    orderItem.setMenu(menu);

	    OrderItem savedOrderItem = orderItemRepo.saveAndFlush(orderItem);

	    return new OrderItemDTO(
	            savedOrderItem.getOrderItemId(),
	            savedOrderItem.getQuantity(),
	            savedOrderItem.getPrice(),
	            savedOrderItem.getOrder().getOrderId(),
	            savedOrderItem.getMenu().getMenuId()
	    );
	}


	public OrderItemDTO updateOrderItem(int orderItemId, OrderItemRequestDTO orderItemDTO) {

	    OrderItem existingOrderItem = orderItemRepo.findById(orderItemId)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND,
	                    "OrderItem not found with id: " + orderItemId
	            ));

	    Order order = orderRepo.findById(orderItemDTO.getOrderId())
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND,
	                    "Order not found with id: " + orderItemDTO.getOrderId()
	            ));

	    Menu menu = menuRepo.findById(orderItemDTO.getMenuId())
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND,
	                    "Menu not found with id: " + orderItemDTO.getMenuId()
	            ));

	    existingOrderItem.setQuantity(orderItemDTO.getQuantity());
	    existingOrderItem.setPrice(orderItemDTO.getPrice());
	    existingOrderItem.setOrder(order);
	    existingOrderItem.setMenu(menu);

	    OrderItem updatedOrderItem = orderItemRepo.save(existingOrderItem);

	    return new OrderItemDTO(
	            updatedOrderItem.getOrderItemId(),
	            updatedOrderItem.getQuantity(),
	            updatedOrderItem.getPrice(),
	            updatedOrderItem.getOrder().getOrderId(),
	            updatedOrderItem.getMenu().getMenuId()
	    );
	}

	public void deleteOrderItem(int orderItemId) {

		if (!orderItemRepo.existsById(orderItemId)) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND,
					"OrderItem not found with id: " + orderItemId
					);
		}

		orderItemRepo.deleteById(orderItemId);
	}
}