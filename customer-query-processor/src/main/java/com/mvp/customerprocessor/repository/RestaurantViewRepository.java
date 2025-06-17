package com.mvp.customerprocessor.repository;

import com.mvp.customerprocessor.entity.RestaurantView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantViewRepository extends MongoRepository<RestaurantView, String> {

    Optional<RestaurantView> findByNameContainingIgnoreCase(String namePart);

    Optional<RestaurantView> findByNameIgnoreCase(String name);

    Optional<RestaurantView> findByRestaurantId(Long restaurantId);
}
