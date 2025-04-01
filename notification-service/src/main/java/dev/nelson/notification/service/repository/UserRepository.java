package dev.nelson.notification.service.repository;

import dev.nelson.notification.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Nelson Tanko
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.email FROM User u WHERE u.notificationEnabled = true")
    List<String> findAllEmailAndNotificationEnabled();
}
