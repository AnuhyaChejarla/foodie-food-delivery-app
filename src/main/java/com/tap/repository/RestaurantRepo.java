package com.tap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tap.model.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {
	
	
}
