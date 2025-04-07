package org.cat.fish.routeservice.repository;

import org.cat.fish.routeservice.model.entity.RoutePhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutePhotosRepository extends JpaRepository<RoutePhotos, Long> {
    List<RoutePhotos> findByRouteRouteId(Long routeId);

    @Modifying
    @Query("DELETE FROM RoutePhotos p WHERE p.route.routeId = :routeId")
    void deleteByRouteRouteId(@Param("routeId") Long routeId);
}
