package com.mvp.customerprocessor.controller;

import com.mvp.customerprocessor.dto.RestaurantCreateDto;
import com.mvp.customerprocessor.dto.RestaurantDto;
import com.mvp.customerprocessor.dto.RestaurantUpdateDto;
import com.mvp.customerprocessor.service.restaurant.RestaurantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantDto saveRestaurant(@RequestBody @Valid RestaurantCreateDto restaurantCreateDto) {
        return restaurantService.save(restaurantCreateDto);
    }

    @DeleteMapping("/{restaurantId}")
    public RestaurantDto deleteRestaurant(@PathVariable @Valid @Positive Long restaurantId) {
        return restaurantService.delete(restaurantId);
    }

    @PutMapping("/{restaurantId}")
    public RestaurantDto updateRestaurant(
            @PathVariable @Valid @Positive Long restaurantId,
            @RequestBody @Valid RestaurantUpdateDto restaurantUpdateDto
    ) {
        return restaurantService.update(restaurantId, restaurantUpdateDto);
    }
}
