package org.cat.fish.tripservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "trips")
@Entity
@Table(name = "trip_client")
public class TripClient {
    @Id
    @Column(name = "client_id", unique = true, nullable = false, updatable = false)
    private Long clientId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(mappedBy = "clients", fetch = FetchType.LAZY)
    private Set<Trip> trips = new HashSet<>();
}