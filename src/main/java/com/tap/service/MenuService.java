package com.tap.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tap.dto.MenuDTO;
import com.tap.model.Menu;
import com.tap.model.Restaurant;
import com.tap.repository.MenuRepo;
import com.tap.repository.RestaurantRepo;


@Service
public class MenuService {

    private MenuRepo menuRepo;
    private RestaurantRepo restaurantRepo;

    public MenuService(MenuRepo menuRepo, RestaurantRepo restaurantRepo) {
        this.menuRepo = menuRepo;
        this.restaurantRepo = restaurantRepo;
    }

    public List<MenuDTO> getMenu() {

        return menuRepo.findAll()
                .stream()
                .map(menu -> new MenuDTO(
                        menu.getMenuId(),
                        menu.getItemName(),
                        menu.getPrice(),
                        menu.getDescription(),
                        menu.getCategory(),
                        menu.getImagePath(),
                        menu.isAvailable(),
                        menu.getRestaurant().getRestaurantId()
                ))
                .toList();
    }

    public MenuDTO getMenuById(int menuId) {

        Menu menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Menu not found with id: " + menuId
                ));

        return new MenuDTO(
                menu.getMenuId(),
                menu.getItemName(),
                menu.getPrice(),
                menu.getDescription(),
                menu.getCategory(),
                menu.getImagePath(),
                menu.isAvailable(),
                menu.getRestaurant().getRestaurantId()
        );
    }
    
    public MenuDTO addMenu(MenuDTO menuDTO) {

        Restaurant restaurant = restaurantRepo.findById(menuDTO.getRestaurantId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Restaurant not found with id: " + menuDTO.getRestaurantId()
                ));

        Menu menu = new Menu();

        menu.setMenuId(menuDTO.getMenuId());
        menu.setItemName(menuDTO.getItemName());
        menu.setPrice(menuDTO.getPrice());
        menu.setDescription(menuDTO.getDescription());
        menu.setCategory(menuDTO.getCategory());
        menu.setImagePath(menuDTO.getImagePath());
        menu.setAvailable(menuDTO.isAvailable());
        menu.setRestaurant(restaurant);

        Menu savedMenu = menuRepo.save(menu);

        return new MenuDTO(
                savedMenu.getMenuId(),
                savedMenu.getItemName(),
                savedMenu.getPrice(),
                savedMenu.getDescription(),
                savedMenu.getCategory(),
                savedMenu.getImagePath(),
                savedMenu.isAvailable(),
                savedMenu.getRestaurant().getRestaurantId()
        );
    }

    public MenuDTO updateMenu(int menuId, MenuDTO menuDTO) {

        Menu existingMenu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Menu not found with id: " + menuId
                ));

        Restaurant restaurant = restaurantRepo.findById(menuDTO.getRestaurantId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Restaurant not found with id: " + menuDTO.getRestaurantId()
                ));

        existingMenu.setItemName(menuDTO.getItemName());
        existingMenu.setPrice(menuDTO.getPrice());
        existingMenu.setDescription(menuDTO.getDescription());
        existingMenu.setCategory(menuDTO.getCategory());
        existingMenu.setImagePath(menuDTO.getImagePath());
        existingMenu.setAvailable(menuDTO.isAvailable());
        existingMenu.setRestaurant(restaurant);

        Menu updatedMenu = menuRepo.save(existingMenu);

        return new MenuDTO(
                updatedMenu.getMenuId(),
                updatedMenu.getItemName(),
                updatedMenu.getPrice(),
                updatedMenu.getDescription(),
                updatedMenu.getCategory(),
                updatedMenu.getImagePath(),
                updatedMenu.isAvailable(),
                updatedMenu.getRestaurant().getRestaurantId()
        );
    }
    public void deleteMenu(int menuId) {
        menuRepo.deleteById(menuId);
    }
}