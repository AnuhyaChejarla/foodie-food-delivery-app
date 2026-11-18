package com.tap.dto;

import java.math.BigDecimal;

public class MenuDTO {

    private int menuId;
    private String itemName;
    private BigDecimal price;
    private String description;
    private String category;
    private String imagePath;
    private boolean available;
    private int restaurantId;

    public MenuDTO() {
    }

    public MenuDTO(int menuId, String itemName, BigDecimal price, String description,
                   String category, String imagePath, boolean available, int restaurantId) {

        this.menuId = menuId;
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imagePath = imagePath;
        this.available = available;
        this.restaurantId = restaurantId;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}