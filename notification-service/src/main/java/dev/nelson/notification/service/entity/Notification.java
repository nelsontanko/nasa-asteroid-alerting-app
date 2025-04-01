package dev.nelson.notification.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Nelson Tanko
 */
@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String asteroidName;
    private LocalDate closeApproachDate;
    private BigDecimal missDistanceKilometers;
    private double estimatedDiameterAvgMeters;
    private boolean emailSent;
}
