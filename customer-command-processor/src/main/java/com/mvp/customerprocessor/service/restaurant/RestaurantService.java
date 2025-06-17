package com.mvp.customerprocessor.service.restaurant;

import com.mvp.customerprocessor.dto.RestaurantCreateDto;
import com.mvp.customerprocessor.dto.RestaurantDto;
import com.mvp.customerprocessor.dto.RestaurantUpdateDto;

public interface RestaurantService {
    RestaurantDto save(RestaurantCreateDto restaurantCreateDto);
    RestaurantDto delete(Long restaurantId);
    RestaurantDto update(Long restaurantId, RestaurantUpdateDto restaurantUpdateDto);
}
