package dev.nelson.notification.service.service;

import dev.nelson.notification.service.entity.Notification;
import dev.nelson.notification.service.repository.NotificationRepository;
import dev.nelson.notification.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nelson Tanko
 */
@Slf4j
@Service
public class EmailService {
    @Value("${application.email.from}")
    private String fromEmail;

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final JavaMailSender javaMailSender;

    public EmailService(NotificationRepository notificationRepository,
                        UserRepository userRepository, JavaMailSender javaMailSender) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    public void sendAsteroidEmail(){
        final String text = createEmailText();

        if (text == null){
            log.info("No asteroids to send");
        }

        final List<String> toEmails = userRepository.findAllEmailAndNotificationEnabled();
        if (toEmails.isEmpty()){
            log.info("No users with email notifications enabled");
        }
        toEmails.forEach(toEmail -> sendEmail(toEmail, text));
        log.info("Email sent to #{} users", toEmails.size());
    }

    private void sendEmail(String toEmail, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Nasa Asteroid Collision Event");
        message.setText(text);
        javaMailSender.send(message);
    }

    private String createEmailText() {
        List<Notification> notifications = notificationRepository.findByEmailSent(false);
        if (notifications.isEmpty()){
            return null;
        }
        StringBuilder mailText = new StringBuilder();
        mailText.append("Asteroid Alert \n");
        mailText.append("********************************** \n");

        notifications.forEach(notification -> {
            mailText.append("Asteroid name: ").append(notification.getAsteroidName()).append("\n");
            mailText.append("Close approach date: ").append(notification.getCloseApproachDate()).append("\n");
            mailText.append("Estimated diameter (avg): ").append(notification.getEstimatedDiameterAvgMeters()).append("\n");
            mailText.append("Miss distance (km): ").append(notification.getMissDistanceKilometers()).append("\n");
            mailText.append("********************************** \n");
            notification.setEmailSent(true);
            notificationRepository.save(notification);
        });
        return mailText.toString();
    }
}
