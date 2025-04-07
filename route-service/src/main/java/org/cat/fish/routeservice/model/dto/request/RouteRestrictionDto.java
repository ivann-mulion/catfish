package org.cat.fish.routeservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RouteRestrictionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long restrictionId;
    @JsonProperty("route")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RouteDto routeDto;
    private String typeOfRestriction;
}
