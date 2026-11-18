package com.tap.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "menu")
public class Menu {

	@Id
	@Column(name = "menu_id")
	private int menuId;
	@Column(name = "item_name")
	@NotBlank(message = "Item name cannot be blank")
	private String itemName;

	@DecimalMin(value = "0.1", message = "Price must be greater than 0")
	private BigDecimal price;

	@NotBlank(message = "Description cannot be blank")
	private String description;

	@NotBlank(message = "Category cannot be blank")
	private String category;

	@Column(name = "is_available")
	@JsonProperty("available")
	private boolean isAvailable;

	@Column(name = "image_path")
	private String imagePath;

	@NotNull(message = "Restaurant cannot be null")
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	public Menu() {
		// TODO Auto-generated constructor stub
	}

	public Menu(int menuId, String itemName, BigDecimal price, String description, String category, boolean isAvailable,
			String imagePath, Restaurant restaurant) {
		super();
		this.menuId = menuId;
		this.itemName = itemName;
		this.price = price;
		this.description = description;
		this.category = category;
		this.isAvailable = isAvailable;
		this.imagePath = imagePath;
		this.restaurant = restaurant;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", itemName=" + itemName + ", price=" + price + ", description=" + description
				+ ", category=" + category + ", isAvailable=" + isAvailable + ", imagePath=" + imagePath
				+ ", restaurant=" + restaurant + "]";
	}


}














