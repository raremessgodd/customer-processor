package com.mvp.customerprocessor.entity;

import com.mvp.customerprocessor.enums.BookingEventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "booking_event_store")
public class BookingEventStore extends EventStore {

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingEventType type;

    @Column(name = "place_capacity")
    private Integer placeCapacity;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "booking_start_time")
    private LocalDateTime bookingStartTime;

    @Column(name = "booking_end_time")
    private LocalDateTime bookingEndTime;
}
