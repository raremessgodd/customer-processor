package com.mvp.customerprocessor.service.restaurant;

import com.mvp.customerprocessor.dto.RestaurantViewDto;
import com.mvp.customerprocessor.exception.CustomerProcessorRuntimeException;
import com.mvp.customerprocessor.mapper.RestaurantViewMapper;
import com.mvp.customerprocessor.repository.RestaurantViewRepository;
import com.mvp.customerprocessor.util.Constraints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantViewServiceImpl implements RestaurantViewService {

    private final RestaurantViewRepository restaurantViewRepository;
    private final RestaurantViewMapper restaurantViewMapper;

    @Override
    public List<RestaurantViewDto> searchByName(String namePart) {
        if (namePart == null) {
            return restaurantViewRepository.findAll().stream()
                    .map(restaurantViewMapper::toDto)
                    .toList();
        }
        return restaurantViewRepository.findByNameContainingIgnoreCase(namePart).stream()
                .map(restaurantViewMapper::toDto)
                .toList();
    }

    @Override
    @Cacheable(value = Constraints.RESTAURANT_ID_CACHE_NAME, key = "#restaurantId")
    public RestaurantViewDto getById(Long restaurantId) {
        return restaurantViewRepository.findByRestaurantId(restaurantId)
                .map(restaurantViewMapper::toDto)
                .orElseThrow(() -> new CustomerProcessorRuntimeException("Не найден ресторан с id=" + restaurantId));
    }

    @Override
    @Cacheable(value = Constraints.RESTAURANT_NAME_CACHE_NAME, key = "#name")
    public RestaurantViewDto getByName(String name) {
        return restaurantViewRepository.findByNameIgnoreCase(name)
                .map(restaurantViewMapper::toDto)
                .orElseThrow(() -> new CustomerProcessorRuntimeException("Не найден ресторан с name=" + name));
    }
}
