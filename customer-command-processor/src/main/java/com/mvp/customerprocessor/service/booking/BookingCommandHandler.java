package com.mvp.customerprocessor.service.booking;

import com.mvp.customerprocessor.event_source.command.CancelBookingCommand;
import com.mvp.customerprocessor.event_source.command.CompleteBookingCommand;
import com.mvp.customerprocessor.event_source.command.CreateBookingCommand;
import com.mvp.customerprocessor.event_source.command.ModifyBookingCommand;

import java.util.UUID;

public interface BookingCommandHandler {

    UUID handleCreate(CreateBookingCommand createBookingCommand);

    void handleCancel(CancelBookingCommand cancelBookingCommand);

    void handleComplete(CompleteBookingCommand completeBookingCommand);

    void handleModify(ModifyBookingCommand modifyBookingCommand);
}
