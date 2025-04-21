package org.cat.fish.tripservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TripClientDto {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long clientId;
    private String firstName;
    private String lastName;
}
