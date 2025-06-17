package com.mvp.customerprocessor.service.booking;

import com.mvp.customerprocessor.config.filter.UserContextHolder;
import com.mvp.customerprocessor.dto.BookingCreateDto;
import com.mvp.customerprocessor.dto.BookingUpdateDto;
import com.mvp.customerprocessor.entity.Restaurant;
import com.mvp.customerprocessor.event_source.command.CancelBookingCommand;
import com.mvp.customerprocessor.event_source.command.CompleteBookingCommand;
import com.mvp.customerprocessor.event_source.command.CreateBookingCommand;
import com.mvp.customerprocessor.event_source.command.ModifyBookingCommand;
import com.mvp.customerprocessor.exception.CustomerProcessorRuntimeException;
import com.mvp.customerprocessor.mapper.BookingCommandMapper;
import com.mvp.customerprocessor.repository.RestaurantRepository;
import com.mvp.customerprocessor.validator.BookingValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingCommandMapper commandMapper;
    private final BookingCommandHandler bookingCommandHandler;
    private final BookingValidator bookingValidator;
    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public UUID createBooking(BookingCreateDto bookingCreateDto) {
        bookingValidator.validateRestaurant(bookingCreateDto);
        Restaurant restaurant = restaurantRepository.findById(bookingCreateDto.restaurantId())
                .orElseThrow(() -> new CustomerProcessorRuntimeException("Не найден ресторан с id = " + bookingCreateDto.restaurantId()));
        CreateBookingCommand createBookingCommand = commandMapper.toCreateCommand(bookingCreateDto, restaurant.getName(), UserContextHolder.getUserId());
        return bookingCommandHandler.handleCreate(createBookingCommand);
    }

    @Override
    @Transactional
    public void cancelBooking(UUID bookingUid) {
        CancelBookingCommand cancelBookingCommand = commandMapper.toCancelCommand(bookingUid, UserContextHolder.getUserId());
        bookingCommandHandler.handleCancel(cancelBookingCommand);

    }

    @Override
    @Transactional
    public void completeBooking(UUID bookingUid) {
        CompleteBookingCommand completeBookingCommand = commandMapper.toCompleteCommand(bookingUid, UserContextHolder.getUserId());
        bookingCommandHandler.handleComplete(completeBookingCommand);
    }

    @Override
    @Transactional
    public void modifyBooking(BookingUpdateDto bookingUpdateDto, UUID bookingUid) {
        ModifyBookingCommand modifyBookingCommand = commandMapper.toModifyCommand(bookingUpdateDto, bookingUid, UserContextHolder.getUserId());
        bookingCommandHandler.handleModify(modifyBookingCommand);
    }
}
