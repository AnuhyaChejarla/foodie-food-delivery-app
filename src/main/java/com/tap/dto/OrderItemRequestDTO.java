package com.tap.dto;

import java.math.BigDecimal;

public class OrderItemRequestDTO {

    private int orderItemId;
    private int quantity;
    private BigDecimal price;
    private int orderId;
    private int menuId;

    public OrderItemRequestDTO() {
    }

    public OrderItemRequestDTO(int orderItemId, int quantity, BigDecimal price,
                               int orderId, int menuId) {

        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
        this.menuId = menuId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
