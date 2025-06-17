package com.mvp.customerprocessor.service.restaurant;

import com.mvp.customerprocessor.event.RestaurantEventDto;

public interface RestaurantProjectorService {

    void save(RestaurantEventDto restaurantEventDto);
}
