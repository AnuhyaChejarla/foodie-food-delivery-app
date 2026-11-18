package com.tap.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "restaurant")
public class Restaurant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int restaurantId;
	
	@NotBlank(message = "Restaurant name cannot be blank")
	private String name;

	@NotBlank(message = "Cuisine type cannot be blank")
	private String cusineType;

	@Min(value = 1, message = "Delivery time must be at least 1 minute")
	private int deliveryTime;

	@NotBlank(message = "Address cannot be blank")
	private String address;

	@DecimalMin(value = "0.0", message = "Rating cannot be less than 0")
	@DecimalMax(value = "5.0", message = "Rating cannot be greater than 5")
	private float rating;
	@JsonProperty("active")
	private boolean isActive;
	private String imagePath;
	
	public Restaurant() {
		
	}

	public Restaurant(int restaurantId, String name, String cusineType, int deliveryTime, String address, float rating,
			boolean isActive, String imagePath) {
		super();
		this.restaurantId = restaurantId;
		this.name = name;
		this.cusineType = cusineType;
		this.deliveryTime = deliveryTime;
		this.address = address;
		this.rating = rating;
		this.isActive = isActive;
		this.imagePath = imagePath;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCusineType() {
		return cusineType;
	}

	public void setCusineType(String cusineType) {
		this.cusineType = cusineType;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "Restaurant [restaurantId=" + restaurantId + ", name=" + name + ", cusineType=" + cusineType
				+ ", deliveryTime=" + deliveryTime + ", address=" + address + ", rating=" + rating + ", isActive="
				+ isActive + ", imagePath=" + imagePath + "]";
	}
	
	
	
	

}
