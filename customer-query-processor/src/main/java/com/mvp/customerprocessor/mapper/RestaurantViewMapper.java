package com.mvp.customerprocessor.mapper;

import com.mvp.customerprocessor.event.RestaurantEventDto;
import com.mvp.customerprocessor.dto.RestaurantViewDto;
import com.mvp.customerprocessor.entity.RestaurantView;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RestaurantViewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "restaurantId")
    RestaurantView toView(RestaurantEventDto event);

    RestaurantViewDto toDto(RestaurantView view);
}
