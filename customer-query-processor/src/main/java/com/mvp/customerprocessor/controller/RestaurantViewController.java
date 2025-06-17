package com.mvp.customerprocessor.controller;

import com.mvp.customerprocessor.dto.RestaurantViewDto;
import com.mvp.customerprocessor.service.restaurant.RestaurantViewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantViewController {

    private final RestaurantViewService restaurantViewService;

    @GetMapping("/{restaurantId}")
    public RestaurantViewDto getById(@PathVariable @Valid @Positive Long restaurantId) {
        return restaurantViewService.getById(restaurantId);
    }

    @GetMapping("/name/{name}")
    public RestaurantViewDto getByName(@PathVariable @Valid @NotEmpty String name) {
        return restaurantViewService.getByName(name);
    }

    @GetMapping
    public List<RestaurantViewDto> searchByName(@RequestParam(required = false) @Valid @NotEmpty String name) {
        return restaurantViewService.searchByName(name);
    }
}
