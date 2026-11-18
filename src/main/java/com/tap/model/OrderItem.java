package com.tap.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order_items")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderItemId;


	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;

	@DecimalMin(value = "0.1", message = "Price must be greater than 0")
	private BigDecimal price;

	@NotNull(message = "Order cannot be null")
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@NotNull(message = "Menu cannot be null")
	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;

	public OrderItem() {
	}

	public OrderItem(int orderItemId, int quantity, BigDecimal price,
			Order order, Menu menu) {
		this.orderItemId = orderItemId;
		this.quantity = quantity;
		this.price = price;
		this.order = order;
		this.menu = menu;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}