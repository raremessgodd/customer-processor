package com.mvp.customerprocessor.repository;

import com.mvp.customerprocessor.entity.BookingEventStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingEventStoreRepository extends JpaRepository<BookingEventStore, UUID> {

    @Query("select b from BookingEventStore b where b.aggregateUid = ?1 order by b.version")
    List<BookingEventStore> findByBookingUidOrderByVersionAsc(UUID bookingUid);
}
