package com.tap.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {

    private int orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private int userId;

    public OrderDTO() {
    }

    public OrderDTO(int orderId, LocalDateTime orderDate,
    		BigDecimal totalAmount, String status, int userId) {

        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal  getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal  totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
