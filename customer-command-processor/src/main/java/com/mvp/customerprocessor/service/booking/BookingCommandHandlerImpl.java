package com.mvp.customerprocessor.service.booking;

import com.mvp.customerprocessor.entity.BookingEventStore;
import com.mvp.customerprocessor.enums.BookingEventType;
import com.mvp.customerprocessor.event.BookingCancelledEvent;
import com.mvp.customerprocessor.event.BookingCompletedEvent;
import com.mvp.customerprocessor.event.BookingCreatedEvent;
import com.mvp.customerprocessor.event.BookingModifiedEvent;
import com.mvp.customerprocessor.mapper.BookingEventMapper;
import com.mvp.customerprocessor.producer.BookingEventProducer;
import com.mvp.customerprocessor.event_source.aggregate.BookingAggregate;
import com.mvp.customerprocessor.event_source.command.CancelBookingCommand;
import com.mvp.customerprocessor.event_source.command.CompleteBookingCommand;
import com.mvp.customerprocessor.event_source.command.CreateBookingCommand;
import com.mvp.customerprocessor.event_source.command.ModifyBookingCommand;
import com.mvp.customerprocessor.exception.CustomerProcessorRuntimeException;
import com.mvp.customerprocessor.mapper.BookingEventStoreMapper;
import com.mvp.customerprocessor.repository.BookingEventStoreRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BookingCommandHandlerImpl extends AbstractBookingCommandHandler {

    private final BookingEventProducer bookingEventProducer;
    private final BookingEventMapper bookingEventMapper;

    protected BookingCommandHandlerImpl(
            BookingEventStoreRepository bookingEventStoreRepository,
            BookingEventStoreMapper bookingEventStoreMapper,
            BookingEventProducer bookingEventProducer,
            BookingEventMapper bookingEventMapper
    ) {
        super(bookingEventStoreRepository, bookingEventStoreMapper);
        this.bookingEventProducer = bookingEventProducer;
        this.bookingEventMapper = bookingEventMapper;
    }

    @Override
    @Transactional
    public UUID handleCreate(CreateBookingCommand createBookingCommand) {

        BookingEventStore eventStore = bookingEventStoreMapper.toEventStore(createBookingCommand);
        eventStore.setType(BookingEventType.CREATE);
        eventStore = bookingEventStoreRepository.saveAndFlush(eventStore);

        createBookingCommand.setCommandUid(eventStore.getAggregateUid());
        BookingCreatedEvent event = bookingEventMapper.toCreateEvent(createBookingCommand, eventStore.getId());
        bookingEventProducer.sendMessage(event);

        return eventStore.getAggregateUid();
    }

    @Override
    @Transactional
    public void handleCancel(CancelBookingCommand cancelBookingCommand) {

        BookingAggregate aggregate = reconstructAggregate(cancelBookingCommand.getBookingUid());

        if (aggregate.getStatus() == BookingEventType.CANCEL) {
            throw new CustomerProcessorRuntimeException("Бронирование уже отменено");
        }

        BookingEventStore eventStore = bookingEventStoreMapper.toEventStore(cancelBookingCommand, aggregate.getVersion() + 1);
        eventStore.setType(BookingEventType.CANCEL);
        eventStore = bookingEventStoreRepository.saveAndFlush(eventStore);

        BookingCancelledEvent event = bookingEventMapper.toCancelEvent(cancelBookingCommand, eventStore.getVersion(), eventStore.getId());
        bookingEventProducer.sendMessage(event);
    }

    @Override
    @Transactional
    public void handleComplete(CompleteBookingCommand completeBookingCommand) {

        BookingAggregate aggregate = reconstructAggregate(completeBookingCommand.getBookingUid());

        if (aggregate.getStatus() == BookingEventType.CANCEL) {
            throw new CustomerProcessorRuntimeException("Невозможно завершить отмененное бронирование");
        }

        BookingEventStore eventStore = bookingEventStoreMapper.toEventStore(completeBookingCommand, aggregate.getVersion() + 1);
        eventStore.setType(BookingEventType.COMPLETE);
        eventStore = bookingEventStoreRepository.saveAndFlush(eventStore);

        BookingCompletedEvent event = bookingEventMapper.toCompleteEvent(completeBookingCommand, eventStore.getVersion(), eventStore.getId());
        bookingEventProducer.sendMessage(event);
    }

    @Override
    @Transactional
    public void handleModify(ModifyBookingCommand modifyBookingCommand) {

        BookingAggregate aggregate = reconstructAggregate(modifyBookingCommand.getBookingUid());

        if (aggregate.getStatus() != BookingEventType.CREATE && aggregate.getStatus() != BookingEventType.MODIFY) {
            throw new CustomerProcessorRuntimeException("Невозможно отменить звершенной или отмененное бронирование");
        }

        BookingEventStore eventStore = bookingEventStoreMapper.toEventStore(modifyBookingCommand, aggregate.getVersion() + 1);
        eventStore.setType(BookingEventType.MODIFY);
        eventStore = bookingEventStoreRepository.saveAndFlush(eventStore);

        BookingModifiedEvent event = bookingEventMapper.toModifyEvent(modifyBookingCommand, eventStore.getVersion(), eventStore.getId());
        bookingEventProducer.sendMessage(event);
    }
}
