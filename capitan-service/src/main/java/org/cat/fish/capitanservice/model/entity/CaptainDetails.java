package org.cat.fish.capitanservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "captain")
public class CaptainDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "captain_id", unique = true, nullable = false, updatable = false)
    private Long captainId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "passport_details")
    private String passportDetails;

    @Column(name = "rate_per_hour")
    private Integer ratePerHour;

    @Column(name = "current_account")
    private String currentAccount;
}
