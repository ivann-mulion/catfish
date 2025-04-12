package org.catfish.managerservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Entity
@Table(name = "manager")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ManagerDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id", unique = true, nullable = false, updatable = false)
    private Long managerId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "pasport_details")
    private String pasportDetails;

    @Column(name = "rate_per_hour")
    private Integer ratePerHour;

    @Column(name = "current_account")
    private String currentAccount;
}
