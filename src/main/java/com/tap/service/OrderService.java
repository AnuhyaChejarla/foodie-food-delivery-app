
package com.tap.service;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tap.dto.CheckoutRequestDTO;
import com.tap.dto.CheckoutItemDTO;
import com.tap.dto.OrderDTO;
import com.tap.dto.OrderRequestDTO;

import com.tap.model.Menu;
import com.tap.model.Order;
import com.tap.model.OrderItem;
import com.tap.model.User;

import com.tap.repository.MenuRepo;
import com.tap.repository.OrderItemRepo;
import com.tap.repository.OrderRepo;
import com.tap.repository.UserRepo;

@Service
public class OrderService {

    private OrderRepo orderRepo;
    private UserRepo userRepo;
    private MenuRepo menuRepo;
    private OrderItemRepo orderItemRepo;

    public OrderService(
            OrderRepo orderRepo,
            UserRepo userRepo,
            MenuRepo menuRepo,
            OrderItemRepo orderItemRepo) {

        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.menuRepo = menuRepo;
        this.orderItemRepo = orderItemRepo;
    }

    // Get all orders
    public List<OrderDTO> getOrders() {

        return orderRepo.findAll()
                .stream()
                .map(order -> new OrderDTO(
                        order.getOrderId(),
                        order.getOrderDate(),
                        order.getTotalAmount(),
                        order.getStatus(),
                        order.getUser().getUserId()
                ))
                .toList();
    }

    // Get order by ID
    public OrderDTO getOrderById(int orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with id: " + orderId
                ));

        return new OrderDTO(
                order.getOrderId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getUser().getUserId()
        );
    }

    // Add order
    public OrderDTO addOrder(OrderRequestDTO orderDTO) {

        User user = userRepo.findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + orderDTO.getUserId()
                ));

        Order order = new Order();

        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());
        order.setUser(user);

        Order savedOrder = orderRepo.save(order);

        return new OrderDTO(
                savedOrder.getOrderId(),
                savedOrder.getOrderDate(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getUser().getUserId()
        );
    }

    // Update order
    public OrderDTO updateOrder(int orderId, OrderRequestDTO orderDTO) {

        Order existingOrder = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with id: " + orderId
                ));

        User user = userRepo.findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + orderDTO.getUserId()
                ));

        existingOrder.setOrderDate(orderDTO.getOrderDate());
        existingOrder.setTotalAmount(orderDTO.getTotalAmount());
        existingOrder.setStatus(orderDTO.getStatus());
        existingOrder.setUser(user);

        Order updatedOrder = orderRepo.save(existingOrder);

        return new OrderDTO(
                updatedOrder.getOrderId(),
                updatedOrder.getOrderDate(),
                updatedOrder.getTotalAmount(),
                updatedOrder.getStatus(),
                updatedOrder.getUser().getUserId()
        );
    }

    // Delete order
    public void deleteOrder(int orderId) {

        if (!orderRepo.existsById(orderId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Order not found with id: " + orderId
            );
        }

        orderRepo.deleteById(orderId);
    }

    // Get orders by user ID
    public List<OrderDTO> getOrdersByUserId(int userId) {

        return orderRepo.findByUserUserId(userId)
                .stream()
                .map(order -> new OrderDTO(
                        order.getOrderId(),
                        order.getOrderDate(),
                        order.getTotalAmount(),
                        order.getStatus(),
                        order.getUser().getUserId()
                ))
                .toList();
    }

    // Checkout and place order
    @Transactional
    public OrderDTO checkout(CheckoutRequestDTO request) {

        // 1. Find customer
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        // 2. Validate cart
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Your cart is empty"
            );
        }

        BigDecimal subtotal = BigDecimal.ZERO;

        // 3. Calculate subtotal using database prices
        for (CheckoutItemDTO item : request.getItems()) {

            if (item.getQuantity() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid item quantity"
                );
            }

            Menu menu = menuRepo.findById(item.getMenuId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Menu item not found: " + item.getMenuId()
                    ));

            if (!menu.isAvailable()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        menu.getItemName() + " is currently unavailable"
                );
            }

            BigDecimal itemTotal = menu.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            subtotal = subtotal.add(itemTotal);
        }

        // 4. Calculate delivery fee
        BigDecimal deliveryFee = subtotal.compareTo(
                new BigDecimal("250")) > 0
                ? BigDecimal.ZERO
                : new BigDecimal("30");

        BigDecimal grandTotal = subtotal.add(deliveryFee);

        // 5. Create order
        Order order = new Order();

        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        order.setTotalAmount(grandTotal);
        order.setUser(user);

        // Save delivery details
        order.setDeliveryName(request.getDeliveryName());
        order.setDeliveryPhone(request.getDeliveryPhone());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setDeliveryCity(request.getDeliveryCity());
        order.setDeliveryPincode(request.getDeliveryPincode());

        Order savedOrder = orderRepo.save(order);

        // 6. Save order items
        for (CheckoutItemDTO item : request.getItems()) {

            Menu menu = menuRepo.findById(item.getMenuId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Menu item not found"
                    ));

            OrderItem orderItem = new OrderItem();

            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(menu.getPrice());
            orderItem.setOrder(savedOrder);
            orderItem.setMenu(menu);

            orderItemRepo.save(orderItem);
        }

        // 7. Return order details
        return new OrderDTO(
                savedOrder.getOrderId(),
                savedOrder.getOrderDate(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getUser().getUserId()
        );
    }
}
