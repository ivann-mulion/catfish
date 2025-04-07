package org.cat.fish.routeservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "route")
public class Route implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id", unique = true, nullable = false, updatable = false)
    private Long routeId;

    @OneToMany(mappedBy = "route",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL)
    private List<RoutePhotos> routePhotos = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
