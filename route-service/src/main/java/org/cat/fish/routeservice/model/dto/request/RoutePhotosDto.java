package org.cat.fish.routeservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cat.fish.routeservice.model.entity.Route;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoutePhotosDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long photoId;
    @JsonProperty("route")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RouteDto routeDto;
    private String photoLink;
}
