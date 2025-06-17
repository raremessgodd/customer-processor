package com.mvp.customerprocessor.mapper;

import com.mvp.customerprocessor.dto.RestaurantCreateDto;
import com.mvp.customerprocessor.dto.RestaurantDto;
import com.mvp.customerprocessor.dto.RestaurantUpdateDto;
import com.mvp.customerprocessor.entity.Restaurant;
import com.mvp.customerprocessor.event.RestaurantEventDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RestaurantMapper {

    Restaurant toEntity(RestaurantCreateDto restaurantCreateDto);

    RestaurantDto toDto(Restaurant restaurant);

    void toUpdateEntity(@MappingTarget Restaurant restaurant, RestaurantUpdateDto restaurantUpdateDto);

    RestaurantEventDto toEvent(RestaurantDto restaurantDto);
}
