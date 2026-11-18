package com.tap.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tap.dto.OrderItemDTO;
import com.tap.dto.OrderItemRequestDTO;
import com.tap.service.OrderItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/order-item")
public class OrderItemController {

    private OrderItemService orderItemService;
    
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Operation(
    	    summary = "Get all order items",
    	    description = "Returns a list of all order items"
    	)
    	@ApiResponse(
    	    responseCode = "200",
    	    description = "Order items retrieved successfully"
    	)
    	@GetMapping
    	public List<OrderItemDTO> getOrderItems() {
    	    return orderItemService.getOrderItems();
    	}
    
    @Operation(
    	    summary = "Get order item by ID",
    	    description = "Returns an order item for the given order item ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Order item found"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Order item not found"
    	    )
    	})
    	@GetMapping("/{orderItemId}")
    	public OrderItemDTO getOrderItemById(@PathVariable int orderItemId) {
    	    return orderItemService.getOrderItemById(orderItemId);
    	}
    
    @Operation(
    	    summary = "Create a new order item",
    	    description = "Creates a new order item using the provided order item details"
    	)
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Order item created successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid order item data"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Order or menu not found"
        )
    })
    	@PostMapping
    	public OrderItemDTO addOrderItem(@Valid @RequestBody OrderItemRequestDTO orderItemDTO) {
    	    return orderItemService.addOrderItem(orderItemDTO);
    	}

    @Operation(
    	    summary = "Update an order item",
    	    description = "Updates an existing order item by order item ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Order item updated successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "400",
    	        description = "Invalid order item data"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Order item, order, or menu not found"
    	    )
    	})
    	@PutMapping("/{orderItemId}")
    	public OrderItemDTO updateOrderItem(
    	        @PathVariable int orderItemId,
    	        @Valid @RequestBody OrderItemRequestDTO orderItemDTO) {

    	    return orderItemService.updateOrderItem(orderItemId, orderItemDTO);
    	}
    
    @Operation(
    	    summary = "Delete an order item",
    	    description = "Deletes an existing order item by order item ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Order item deleted successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Order item not found"
    	    )
    	})
    	@DeleteMapping("/{orderItemId}")
    	public String deleteOrderItem(@PathVariable int orderItemId) {
    	    orderItemService.deleteOrderItem(orderItemId);
    	    return "OrderItem deleted successfully";
    	}
}