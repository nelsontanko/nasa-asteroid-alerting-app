package dev.nelson.asteroid.alerting.event;

import lombok.Data;

/**
 * @author Nelson Tanko
 */
@Data
public class AsteroidCollisionEvent {
    private String asteroidName;
    private String closeApproachDate;
    private String missDistanceKilometers;
    private double estimatedDiameterAvgMeters;
}
