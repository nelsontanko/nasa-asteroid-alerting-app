package dev.nelson.notification.service.repository;

import dev.nelson.notification.service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Nelson Tanko
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByEmailSent(boolean b);
}
