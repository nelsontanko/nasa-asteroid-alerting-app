package dev.nelson.notification.service.service;

import dev.nelson.asteroid.alerting.event.AsteroidCollisionEvent;
import dev.nelson.notification.service.entity.Notification;
import dev.nelson.notification.service.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Nelson Tanko
 */
@Slf4j
@Service
public class NotificationService {

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    public NotificationService(EmailService emailService, NotificationRepository notificationRepository) {
        this.emailService = emailService;
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics = "asteroid-alert", groupId = "notification-service")
    public void alertEvent(AsteroidCollisionEvent event){
        log.info("Received asteroid alert: {}", event);

        final Notification notification = Notification.builder()
                .asteroidName(event.getAsteroidName())
                .closeApproachDate(LocalDate.parse(event.getCloseApproachDate()))
                .estimatedDiameterAvgMeters(event.getEstimatedDiameterAvgMeters())
                .missDistanceKilometers(new BigDecimal(event.getMissDistanceKilometers()))
                .emailSent(false)
                .build();
        final Notification savedNotification = notificationRepository.saveAndFlush(notification);
        log.info("Notification saved: {}", savedNotification);
    }

    @Scheduled(fixedRate = 10000)
    public void sendAlertingEmail() {
        log.info("Triggering scheduled job to send email alerts");
        emailService.sendAsteroidEmail();
    }
}
