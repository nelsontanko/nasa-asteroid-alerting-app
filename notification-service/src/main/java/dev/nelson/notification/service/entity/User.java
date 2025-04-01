package dev.nelson.notification.service.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Nelson Tanko
 */
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;
    private String email;
    private boolean notificationEnabled;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", notificationEnabled=" + notificationEnabled +
                '}';
    }
}
