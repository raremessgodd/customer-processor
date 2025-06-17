package com.mvp.customerprocessor.service.booking;

import com.mvp.customerprocessor.entity.BookingEventStore;
import com.mvp.customerprocessor.event_source.aggregate.BookingAggregate;
import com.mvp.customerprocessor.exception.CustomerProcessorRuntimeException;
import com.mvp.customerprocessor.mapper.BookingEventStoreMapper;
import com.mvp.customerprocessor.repository.BookingEventStoreRepository;

import java.util.List;
import java.util.UUID;

public abstract class AbstractBookingCommandHandler implements BookingCommandHandler {

    protected final BookingEventStoreRepository bookingEventStoreRepository;
    protected final BookingEventStoreMapper bookingEventStoreMapper;

    protected AbstractBookingCommandHandler(BookingEventStoreRepository bookingEventStoreRepository, BookingEventStoreMapper bookingEventStoreMapper) {
        this.bookingEventStoreRepository = bookingEventStoreRepository;
        this.bookingEventStoreMapper = bookingEventStoreMapper;
    }

    protected BookingAggregate reconstructAggregate(UUID bookingUid) {

        List<BookingEventStore> events = bookingEventStoreRepository.findByBookingUidOrderByVersionAsc(bookingUid);

        if (events.isEmpty()) {
            throw new CustomerProcessorRuntimeException("Booking not found");
        }

        BookingAggregate aggregate = new BookingAggregate(bookingUid);

        events.forEach(eventStore -> {
            switch (eventStore.getType()) {
                case CREATE -> aggregate.apply(bookingEventStoreMapper.toCreateEvent(eventStore, eventStore.getId()));
                case MODIFY ->  aggregate.apply(bookingEventStoreMapper.toModifyEvent(eventStore, eventStore.getId()));
                case COMPLETE ->  aggregate.apply(bookingEventStoreMapper.toCompleteEvent(eventStore, eventStore.getId()));
                case CANCEL -> aggregate.apply(bookingEventStoreMapper.toCancelEvent(eventStore, eventStore.getId()));
            }
        });

        return aggregate;
    }
}
