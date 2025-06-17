package com.mvp.customerprocessor.repository;

import com.mvp.customerprocessor.entity.BookingView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingViewRepository extends MongoRepository<BookingView, String> {

    Optional<BookingView> findByBookingUid(UUID bookingUid);

    List<BookingView> findByCustomerUid(UUID customerUid);

    List<BookingView> findByRestaurantId(Long restaurantId);

    List<BookingView> findByRestaurantIdAndBookingDate(Long restaurantId, LocalDate date);

    @Query("{'restaurantId': ?0, 'bookingStartTime': {$lt: ?2}, 'bookingEndTime': {$gt: ?1}}")
    List<BookingView> findConflictingBookings(Long restaurantId, LocalDateTime startTime, LocalDateTime endTime);
}
