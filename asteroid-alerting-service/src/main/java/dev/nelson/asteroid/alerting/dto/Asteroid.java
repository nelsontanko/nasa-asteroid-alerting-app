package dev.nelson.asteroid.alerting.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Nelson Tanko
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Asteroid {
    private String id;
    private String name;

    @JsonProperty("estimated_diameter")
    private EstimatedDiameter estimatedDiameter;

    @JsonProperty("close_approach_data")
    private List<CloseApproachData> closeApproachData;

    @JsonProperty("is_potentially_hazardous_asteroid")
    private boolean potentiallyHazardous;

}
