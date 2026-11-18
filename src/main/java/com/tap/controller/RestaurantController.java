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

import com.tap.dto.RestaurantDTO;
import com.tap.dto.RestaurantRequestDTO;
import com.tap.service.RestaurantService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(
    	    summary = "Get all restaurants",
    	    description = "Returns a list of all restaurants"
    	)
    	@ApiResponse(
    	    responseCode = "200",
    	    description = "Restaurants retrieved successfully"
    	)
    	@GetMapping
    	public List<RestaurantDTO> getRestaurants() {
    	    return restaurantService.getRestaurants();
    	}

    @Operation(
    	    summary = "Get restaurant by ID",
    	    description = "Returns a restaurant for the given restaurant ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Restaurant found"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Restaurant not found"
    	    )
    	})
    	@GetMapping("/{restaurantId}")
    	public RestaurantDTO getRestaurantById(
    	        @PathVariable int restaurantId) {

    	    return restaurantService.getRestaurantById(restaurantId);
    	}

    @Operation(
    	    summary = "Add a new restaurant",
    	    description = "Creates a new restaurant and returns the created restaurant"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Restaurant created successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "400",
    	        description = "Invalid restaurant data"
    	    )
    	})
    	@PostMapping
    	public RestaurantDTO addRestaurant(
    	        @Valid @RequestBody RestaurantRequestDTO restaurantDTO) {

    	    return restaurantService.addRestaurant(restaurantDTO);
    	}

    @PutMapping("/{restaurantId}")
    public RestaurantDTO updateRestaurant(
            @PathVariable int restaurantId,
            @Valid @RequestBody RestaurantRequestDTO restaurantDTO) {

        return restaurantService.updateRestaurant(
                restaurantId,
                restaurantDTO
        );
    }

    @Operation(
    	    summary = "Delete a restaurant",
    	    description = "Deletes an existing restaurant using its restaurant ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Restaurant deleted successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Restaurant not found"
    	    )
    	})
    	@DeleteMapping("/{restaurantId}")
    	public String deleteRestaurant(
    	        @PathVariable int restaurantId) {

    	    restaurantService.deleteRestaurant(restaurantId);

    	    return "Restaurant deleted successfully";
    	}
}