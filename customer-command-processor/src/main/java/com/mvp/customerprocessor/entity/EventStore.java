package com.mvp.customerprocessor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public class EventStore {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @UuidGenerator
    private UUID id;

    @Column(name = "customer_uid")
    private UUID customerUid;

    @Column(name = "aggregate_uid", nullable = false)
    private UUID aggregateUid;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "version", nullable = false)
    private int version;
}
