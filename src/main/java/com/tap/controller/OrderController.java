package com.tap.controller;

import java.util.List;
import com.tap.dto.CheckoutRequestDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tap.dto.OrderDTO;
import com.tap.dto.OrderRequestDTO;
import com.tap.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
    	    summary = "Get all orders",
    	    description = "Returns a list of all orders"
    	)
    	@ApiResponse(
    	    responseCode = "200",
    	    description = "Orders retrieved successfully"
    	)
    	@GetMapping
    	public List<OrderDTO> getOrders() {
    	    return orderService.getOrders();
    	}
    
    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrdersByUserId(@PathVariable int userId) {
        return orderService.getOrdersByUserId(userId);
    }
    

    @Operation(
    	    summary = "Get order by ID",
    	    description = "Returns an order for the given order ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Order found"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Order not found"
    	    )
    	})
    	@GetMapping("/{orderId}")
    	public OrderDTO getOrderById(@PathVariable int orderId) {
    	    return orderService.getOrderById(orderId);
    	}
    
    
    @Operation(
    	    summary = "Create a new order",
    	    description = "Creates a new order for a user"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Order created successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "400",
    	        description = "Invalid order data"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "User not found"
    	    )
    	})
    	@PostMapping
    	public OrderDTO addOrder(@Valid @RequestBody OrderRequestDTO orderDTO) {
    	    return orderService.addOrder(orderDTO);
    	}
    @Operation(
    	    summary = "Update an order",
    	    description = "Updates an existing order by order ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Order updated successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Order not found"
    	    ),
    	    @ApiResponse(
    	        responseCode = "400",
    	        description = "Invalid order data"
    	    )
    	})
    
    @PostMapping("/checkout")
    public OrderDTO checkout(@RequestBody CheckoutRequestDTO request) {
        return orderService.checkout(request);
    }
    
    	@PutMapping("/{orderId}")
    	public OrderDTO updateOrder(
    	        @PathVariable int orderId,
    	        @Valid @RequestBody OrderRequestDTO orderDTO) {

    	    return orderService.updateOrder(orderId, orderDTO);
    	}

    @Operation(
    	    summary = "Delete an order",
    	    description = "Deletes an existing order by order ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Order deleted successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Order not found"
    	    )
    	})
    	@DeleteMapping("/{orderId}")
    	public String deleteOrder(@PathVariable int orderId) {
    	    orderService.deleteOrder(orderId);
    	    return "Order deleted successfully";
    	}
}