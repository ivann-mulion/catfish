package org.cat.fish.capitanservice.model.dto.request;

import lombok.*;

import java.io.Serial;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Builder
public class CaptainDetailsDto {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long captainId;
    private Long userId;
    private String passportDetails;
    private Integer ratePerHour;
    private String currentAccount;
}
