package dev.nelson.asteroid.alerting.service;

import dev.nelson.asteroid.alerting.client.NasaClient;
import dev.nelson.asteroid.alerting.dto.Asteroid;
import dev.nelson.asteroid.alerting.event.AsteroidCollisionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Nelson Tanko
 */
@Slf4j
@Service
public class AsteroidAlertingService {

    private final NasaClient nasaClient;
    private final KafkaTemplate<String, AsteroidCollisionEvent> kafkaTemplate;

    public AsteroidAlertingService(NasaClient nasaClient, KafkaTemplate<String, AsteroidCollisionEvent> kafkaTemplate) {
        this.nasaClient = nasaClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void alert() {
        log.info("Alerting service called");

        final LocalDate fromDate = LocalDate.now();
        final LocalDate toDate = LocalDate.now().plusDays(7);
        log.info("Getting asteroids from {} to {}", fromDate, toDate);

        List<Asteroid> asteroids = nasaClient.getNeoAsteroids(fromDate, toDate);
        log.info("Retrieved asteroids list of size {}", asteroids.size());

        List<Asteroid> dangerousAsteroids = asteroids.stream()
                .filter(Asteroid::isPotentiallyHazardous)
                .toList();
        log.info("Found {} hazardous asteroids", dangerousAsteroids.size());

        final List<AsteroidCollisionEvent> asteroidCollisionEvents = createEventOfDangerousAsteroids(dangerousAsteroids);
        log.info("Sending {} asteroid alerts to kafka", asteroidCollisionEvents.size());

        asteroidCollisionEvents.forEach(event -> {
            kafkaTemplate.send("asteroid-alert", event);
            log.info("Asteroid alert sent to kafka topic: {}", event);
        });
    }

    private List<AsteroidCollisionEvent> createEventOfDangerousAsteroids(List<Asteroid> dangerousAsteroids) {
        return dangerousAsteroids.stream()
                .map(asteroid -> {
                    if (asteroid.isPotentiallyHazardous()){
                        return AsteroidCollisionEvent.builder()
                                .asteroidName(asteroid.getName())
                                .closeApproachDate(asteroid.getCloseApproachData().getFirst().getCloseApproachDate().toString())
                                .missDistanceKilometers(asteroid.getCloseApproachData().getFirst().getMissDistance().getKilometers())
                                .estimatedDiameterAvgMeters((asteroid.getEstimatedDiameter().getMeters().getMinDiameter() +
                                        asteroid.getEstimatedDiameter().getMeters().getMaxDiameter()) / 2)
                                .build();
                    }
                    return null;
                })
                .toList();
    }
}
