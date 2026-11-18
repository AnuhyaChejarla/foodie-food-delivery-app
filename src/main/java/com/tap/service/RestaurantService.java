package com.tap.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tap.dto.RestaurantDTO;
import com.tap.dto.RestaurantRequestDTO;
import com.tap.model.Restaurant;
import com.tap.repository.RestaurantRepo;

@Service
public class RestaurantService {

    private RestaurantRepo restaurantRepo;

    public RestaurantService(RestaurantRepo restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    // GET all restaurants
    public List<RestaurantDTO> getRestaurants() {

        return restaurantRepo.findAll()
                .stream()
                .map(restaurant -> new RestaurantDTO(
                        restaurant.getRestaurantId(),
                        restaurant.getName(),
                        restaurant.getCusineType(),
                        restaurant.getDeliveryTime(),
                        restaurant.getAddress(),
                        restaurant.getRating(),
                        restaurant.isActive(),
                        restaurant.getImagePath()
                ))
                .toList();
    }

    // GET restaurant by ID
    public RestaurantDTO getRestaurantById(int restaurantId) {

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Restaurant not found with id: " + restaurantId
                ));

        return new RestaurantDTO(
                restaurant.getRestaurantId(),
                restaurant.getName(),
                restaurant.getCusineType(),
                restaurant.getDeliveryTime(),
                restaurant.getAddress(),
                restaurant.getRating(),
                restaurant.isActive(),
                restaurant.getImagePath()
        );
    }

    // POST restaurant
    public RestaurantDTO addRestaurant(RestaurantRequestDTO restaurantDTO) {

        Restaurant restaurant = new Restaurant();

        restaurant.setName(restaurantDTO.getName());
        restaurant.setCusineType(restaurantDTO.getCusineType());
        restaurant.setDeliveryTime(restaurantDTO.getDeliveryTime());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setRating(restaurantDTO.getRating());
        restaurant.setActive(restaurantDTO.isActive());
        restaurant.setImagePath(restaurantDTO.getImagePath());

        Restaurant savedRestaurant = restaurantRepo.save(restaurant);

        return new RestaurantDTO(
                savedRestaurant.getRestaurantId(),
                savedRestaurant.getName(),
                savedRestaurant.getCusineType(),
                savedRestaurant.getDeliveryTime(),
                savedRestaurant.getAddress(),
                savedRestaurant.getRating(),
                savedRestaurant.isActive(),
                savedRestaurant.getImagePath()
        );
    }

    // PUT restaurant
    public RestaurantDTO updateRestaurant(
            int restaurantId,
            RestaurantRequestDTO restaurantDTO) {

        Restaurant existingRestaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Restaurant not found with id: " + restaurantId
                ));

        existingRestaurant.setName(restaurantDTO.getName());
        existingRestaurant.setCusineType(restaurantDTO.getCusineType());
        existingRestaurant.setDeliveryTime(restaurantDTO.getDeliveryTime());
        existingRestaurant.setAddress(restaurantDTO.getAddress());
        existingRestaurant.setRating(restaurantDTO.getRating());
        existingRestaurant.setActive(restaurantDTO.isActive());
        existingRestaurant.setImagePath(restaurantDTO.getImagePath());

        Restaurant updatedRestaurant =
                restaurantRepo.save(existingRestaurant);

        return new RestaurantDTO(
                updatedRestaurant.getRestaurantId(),
                updatedRestaurant.getName(),
                updatedRestaurant.getCusineType(),
                updatedRestaurant.getDeliveryTime(),
                updatedRestaurant.getAddress(),
                updatedRestaurant.getRating(),
                updatedRestaurant.isActive(),
                updatedRestaurant.getImagePath()
        );
    }

    // DELETE restaurant
    public void deleteRestaurant(int restaurantId) {

        if (!restaurantRepo.existsById(restaurantId)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Restaurant not found with id: " + restaurantId
            );
        }

        restaurantRepo.deleteById(restaurantId);
    }
}