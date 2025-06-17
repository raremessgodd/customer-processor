package com.mvp.customerprocessor.entity;

import com.mvp.customerprocessor.enums.BookingEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(def = "{'restaurantId': 1, 'bookingStartTime': 1}")
@CompoundIndex(def = "{'customerUid': 1, 'bookingStartTime': 1}")
public class BookingView implements Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    private UUID bookingUid;

    @Indexed
    private UUID customerUid;

    @Indexed
    private Long restaurantId;
    private String restaurantName;

    private Integer placeCapacity;
    private LocalDateTime bookingStartTime;
    private LocalDateTime bookingEndTime;

    @Indexed
    private BookingEventType status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDate bookingDate;
    private String bookingDayOfWeek;
}
