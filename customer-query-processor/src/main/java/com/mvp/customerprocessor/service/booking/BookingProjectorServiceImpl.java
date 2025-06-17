package com.mvp.customerprocessor.service.booking;

import com.mvp.customerprocessor.enums.BookingEventType;
import com.mvp.customerprocessor.event.BookingCancelledEvent;
import com.mvp.customerprocessor.event.BookingCompletedEvent;
import com.mvp.customerprocessor.event.BookingCreatedEvent;
import com.mvp.customerprocessor.event.BookingModifiedEvent;
import com.mvp.customerprocessor.entity.BookingView;
import com.mvp.customerprocessor.mapper.BookingViewMapper;
import com.mvp.customerprocessor.repository.BookingViewRepository;
import com.mvp.customerprocessor.util.Constraints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingProjectorServiceImpl implements BookingProjectorService {

    private final BookingViewRepository bookingViewRepository;
    private final BookingViewMapper bookingViewMapper;

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = Constraints.BOOKING_ID_CACHE_NAME, key = "#bookingCreatedEvent.aggregateUid"),
            @CacheEvict(cacheNames = Constraints.BOOKING_CUSTOMER_ID_CACHE_NAME, key = "#bookingCreatedEvent.customerUid")
    })
    public void save(BookingCreatedEvent bookingCreatedEvent) {
        final BookingView bookingView = bookingViewMapper.toView(bookingCreatedEvent);
        bookingView.setStatus(BookingEventType.CREATE);
        bookingViewRepository.save(bookingView);
        log.info("Сохранена запись о брони: {}", bookingCreatedEvent);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = Constraints.BOOKING_ID_CACHE_NAME, key = "#bookingCompletedEvent.aggregateUid"),
            @CacheEvict(cacheNames = Constraints.BOOKING_CUSTOMER_ID_CACHE_NAME, allEntries = true)
    })
    public void complete(BookingCompletedEvent bookingCompletedEvent) {
        bookingViewRepository.findByBookingUid(bookingCompletedEvent.getAggregateUid())
                .ifPresent(view -> {
                    view.setStatus(BookingEventType.COMPLETE);
                    view.setUpdatedAt(bookingCompletedEvent.getCreatedAt());
                    bookingViewRepository.save(view);
                });
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = Constraints.BOOKING_ID_CACHE_NAME, key = "#bookingCancelledEvent.aggregateUid"),
            @CacheEvict(cacheNames = Constraints.BOOKING_CUSTOMER_ID_CACHE_NAME, allEntries = true)
    })
    public void cancel(BookingCancelledEvent bookingCancelledEvent) {
        bookingViewRepository.findByBookingUid(bookingCancelledEvent.getAggregateUid())
                .ifPresent(view -> {
                    view.setStatus(BookingEventType.CANCEL);
                    view.setUpdatedAt(bookingCancelledEvent.getCreatedAt());
                    bookingViewRepository.save(view);
                });
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = Constraints.BOOKING_ID_CACHE_NAME, key = "#bookingModifiedEvent.aggregateUid"),
            @CacheEvict(cacheNames = Constraints.BOOKING_CUSTOMER_ID_CACHE_NAME, allEntries = true)
    })
    public void modify(BookingModifiedEvent bookingModifiedEvent) {
        bookingViewRepository.findByBookingUid(bookingModifiedEvent.getAggregateUid())
                .ifPresent(view -> {
                    if (bookingModifiedEvent.getPlaceCapacity() != null) {
                        view.setPlaceCapacity(bookingModifiedEvent.getPlaceCapacity());
                    }
                    if (bookingModifiedEvent.getBookingStartTime() != null) {
                        view.setBookingStartTime(bookingModifiedEvent.getBookingStartTime());
                        view.setBookingDate(bookingModifiedEvent.getBookingStartTime().toLocalDate());
                    }
                    if (bookingModifiedEvent.getBookingEndTime() != null) {
                        view.setBookingEndTime(bookingModifiedEvent.getBookingEndTime());
                    }

                    view.setUpdatedAt(bookingModifiedEvent.getCreatedAt());
                    bookingViewRepository.save(view);
                });
    }
}
