package org.cat.fish.routeservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cat.fish.routeservice.model.entity.RoutePhotos;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RouteDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long routeId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RoutePhotosDto> routePhotos;
    private String name;
    private String description;
}
