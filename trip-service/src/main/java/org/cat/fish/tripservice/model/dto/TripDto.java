package org.cat.fish.tripservice.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TripDto {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long tripId;
    private Long vesselId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Long routeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double price;
    private Double payment;
    private String status;
}
