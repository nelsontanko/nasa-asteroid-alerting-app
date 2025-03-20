package dev.nelson.asteroid.alerting.client;

import dev.nelson.asteroid.alerting.dto.Asteroid;
import dev.nelson.asteroid.alerting.dto.NasaNeoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Nelson Tanko
 */
@Service
public class NasaClient {

    @Value("${nasa.neo.url}")
    private String neoNasaApiUrl;
    @Value("${nasa.apiKey}")
    private String nasaApiKey;

    public List<Asteroid> getNeoAsteroids(LocalDate fromDate, LocalDate toDate) {
        return Optional.ofNullable(
                new RestTemplate().getForObject(getUrl(fromDate, toDate), NasaNeoResponse.class)
            )
            .map(NasaNeoResponse::getNearEarthObjects)
            .map(Map::values)
            .map(values -> values.stream()
                .flatMap(List::stream)
                .toList())
            .orElse(Collections.emptyList());
    }

    private String getUrl(final LocalDate fromDate, final LocalDate toDate) {
        return UriComponentsBuilder.fromUriString(neoNasaApiUrl)
                .queryParam("start_date", fromDate)
                .queryParam("end_date", toDate)
                .queryParam("api_key", nasaApiKey)
                .toUriString();
    }
}
