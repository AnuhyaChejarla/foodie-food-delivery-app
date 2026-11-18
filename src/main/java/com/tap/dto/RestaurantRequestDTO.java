
package com.tap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RestaurantRequestDTO {

    private Integer restaurantId;

    @NotBlank(message = "Name cannot be blank")
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
    private boolean isActive = true;

    private String imagePath;

    public RestaurantRequestDTO() {
    }

    public RestaurantRequestDTO(Integer restaurantId, String name,
            String cusineType, int deliveryTime, String address,
            float rating, boolean isActive, String imagePath) {

        this.restaurantId = restaurantId;
        this.name = name;
        this.cusineType = cusineType;
        this.deliveryTime = deliveryTime;
        this.address = address;
        this.rating = rating;
        this.isActive = isActive;
        this.imagePath = imagePath;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
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

    @JsonProperty("active")
    public boolean isActive() {
        return isActive;
    }

    @JsonProperty("active")
    public void setActive(boolean active) {
        isActive = active;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
