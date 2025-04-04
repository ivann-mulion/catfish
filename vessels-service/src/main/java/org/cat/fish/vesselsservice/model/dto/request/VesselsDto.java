package org.cat.fish.vesselsservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.cat.fish.vesselsservice.constant.AppConstant;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VesselsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long vesselId;
    private String vesselName;
    private String imageUrl;
    private String description;
    private Integer capacity;
    private Double enginePower;
    private Integer maintenceIntervalHour;
    private Double boatDraftM;
    private Double waterlineHeightM;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = AppConstant.LOCAL_DATE_FORMAT, shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = AppConstant.LOCAL_DATE_FORMAT)
    private LocalDate lastMaintenance;
    private Boolean forSale;
    private Integer minPph;
}
