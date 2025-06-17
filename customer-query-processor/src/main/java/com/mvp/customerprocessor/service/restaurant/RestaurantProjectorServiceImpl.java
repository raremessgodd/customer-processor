package com.mvp.customerprocessor.service.restaurant;

import com.mvp.customerprocessor.event.RestaurantEventDto;
import com.mvp.customerprocessor.entity.RestaurantView;
import com.mvp.customerprocessor.mapper.RestaurantViewMapper;
import com.mvp.customerprocessor.repository.RestaurantViewRepository;
import com.mvp.customerprocessor.util.Constraints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantProjectorServiceImpl implements RestaurantProjectorService {

    private final RestaurantViewRepository restaurantViewRepository;
    private final RestaurantViewMapper restaurantViewMapper;

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = Constraints.RESTAURANT_ID_CACHE_NAME, key = "#restaurantEventDto.id"),
            @CacheEvict(cacheNames = Constraints.RESTAURANT_NAME_CACHE_NAME, key = "#restaurantEventDto.name")
    })
    public void save(RestaurantEventDto restaurantEventDto) {
        final RestaurantView restaurantView = restaurantViewMapper.toView(restaurantEventDto);
        restaurantViewRepository.save(restaurantView);
        log.info("Сохранена запись о ресторане: {}", restaurantEventDto);
    }
}
