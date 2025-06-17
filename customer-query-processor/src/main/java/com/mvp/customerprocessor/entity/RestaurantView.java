package com.mvp.customerprocessor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Document(collection = "restaurants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantView implements Serializable {

    @Id
    private String id;
    private Long restaurantId;
    private String name;
    private LocalTime workingStartTime;
    private LocalTime workingEndTime;
    private Integer maxCapacity;
}
