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

import com.tap.dto.MenuDTO;
import com.tap.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(
    	    summary = "Get all menu items",
    	    description = "Returns a list of all menu items"
    	)
    	@ApiResponse(
    	    responseCode = "200",
    	    description = "Menu items retrieved successfully"
    	)
    	@GetMapping
    	public List<MenuDTO> getMenu() {
    	    return menuService.getMenu();
    	}
    
    @Operation(
    	    summary = "Get menu item by ID",
    	    description = "Returns a menu item for the given menu ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Menu item found"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Menu item not found"
    	    )
    	})
    	@GetMapping("/{menuId}")
    	public MenuDTO getMenuById(@PathVariable int menuId) {
    	    return menuService.getMenuById(menuId);
    	}

    @Operation(
    	    summary = "Add a new menu item",
    	    description = "Creates a new menu item"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Menu item created successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "400",
    	        description = "Invalid menu item data"
    	    )
    	})
    @PostMapping
    public MenuDTO addMenu(@Valid @RequestBody MenuDTO menuDTO) {
        return menuService.addMenu(menuDTO);
    }
    @Operation(
    	    summary = "Update a menu item",
    	    description = "Updates an existing menu item by menu ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Menu item updated successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Menu item or restaurant not found"
    	    )
    	})
    	@PutMapping("/{menuId}")
    	public MenuDTO updateMenu(@PathVariable int menuId,
    	                          @Valid @RequestBody MenuDTO menuDTO) {
    	    return menuService.updateMenu(menuId, menuDTO);
    	}

    
    @Operation(
    	    summary = "Delete a menu item",
    	    description = "Deletes an existing menu item by menu ID"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Menu item deleted successfully"
    	    ),
    	    @ApiResponse(
    	        responseCode = "404",
    	        description = "Menu item not found"
    	    )
    	})
    	@DeleteMapping("/{menuId}")
    	public String deleteMenu(@PathVariable int menuId) {
    	    menuService.deleteMenu(menuId);
    	    return "Menu deleted successfully";
    	}
}