package com.mvp.customerprocessor.validator;

import com.mvp.customerprocessor.dto.BookingCreateDto;
import com.mvp.customerprocessor.repository.BookingEventStoreRepository;
import com.mvp.customerprocessor.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingValidator {

    private final RestaurantRepository restaurantRepository;
    private final BookingEventStoreRepository bookingEventStoreRepository;

    public void validateRestaurant(BookingCreateDto bookingCreateDto) {
        //TODO
    }
}
