package com.mvp.customerprocessor.service.restaurant;

import com.mvp.customerprocessor.dto.RestaurantCreateDto;
import com.mvp.customerprocessor.dto.RestaurantDto;
import com.mvp.customerprocessor.dto.RestaurantUpdateDto;
import com.mvp.customerprocessor.entity.Restaurant;
import com.mvp.customerprocessor.producer.RestaurantEventProducer;
import com.mvp.customerprocessor.exception.CustomerProcessorRuntimeException;
import com.mvp.customerprocessor.mapper.RestaurantMapper;
import com.mvp.customerprocessor.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEventProducer restaurantEventProducer;
    private final RestaurantMapper restaurantMapper;

    @Override
    @Transactional
    public RestaurantDto save(RestaurantCreateDto restaurantCreateDto) {
        final Restaurant restaurant = restaurantMapper.toEntity(restaurantCreateDto);
        final Restaurant savedRestaurant = restaurantRepository.saveAndFlush(restaurant);
        final RestaurantDto restaurantDto = restaurantMapper.toDto(savedRestaurant);
        restaurantEventProducer.sendMessage(restaurantMapper.toEvent(restaurantDto));
        log.info("Сохранен новый ресторан: {}", restaurantCreateDto);
        return restaurantDto;
    }

    @Override
    @Transactional
    public RestaurantDto delete(Long restaurantId) {
        final Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomerProcessorRuntimeException("Не найден ресторан с id=" + restaurantId));
        restaurantRepository.delete(restaurant);
        final RestaurantDto restaurantDto = restaurantMapper.toDto(restaurant);
        restaurantEventProducer.sendMessage(restaurantMapper.toEvent(restaurantDto));
        log.info("Удален ресторан: {}", restaurantDto);
        return restaurantDto;
    }

    @Override
    @Transactional
    public RestaurantDto update(Long restaurantId, RestaurantUpdateDto restaurantUpdateDto) {
        final Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomerProcessorRuntimeException("Не найден ресторан с id=" + restaurantId));
        restaurantMapper.toUpdateEntity(restaurant, restaurantUpdateDto);
        final Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        final RestaurantDto restaurantDto = restaurantMapper.toDto(savedRestaurant);
        restaurantEventProducer.sendMessage(restaurantMapper.toEvent(restaurantDto));
        log.info("Обновлен русторан c id={}: {}", restaurantId, restaurantDto);
        return restaurantDto;
    }
}
