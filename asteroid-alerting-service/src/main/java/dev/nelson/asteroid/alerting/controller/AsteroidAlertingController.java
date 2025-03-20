package dev.nelson.asteroid.alerting.controller;

import dev.nelson.asteroid.alerting.service.AsteroidAlertingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nelson Tanko
 */
@RestController
@RequestMapping("/api/v1/alert")
public class AsteroidAlertingController {

    private final AsteroidAlertingService asteroidAlertingService;

    public AsteroidAlertingController(AsteroidAlertingService asteroidAlertingService) {
        this.asteroidAlertingService = asteroidAlertingService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void alert(){
        asteroidAlertingService.alert();
    }
}
