package com.mvp.customerprocessor.service.restaurant;

import com.mvp.customerprocessor.dto.RestaurantViewDto;

import java.util.List;

public interface RestaurantViewService {
    List<RestaurantViewDto> searchByName(String namePart);
    RestaurantViewDto getById(Long restaurantId);
    RestaurantViewDto getByName(String name);
}
