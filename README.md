# Asteroid Alert System

A Spring Boot application that monitors Near-Earth Objects (NEOs) using NASA's API and sends email notifications for potentially dangerous asteroids.

## Features

- Real-time monitoring of Near-Earth Objects via NASA API
- Risk assessment of approaching asteroids
- Automated email notifications using Java Mail
- Event-driven architecture with Apache Kafka

## Requirements

- Java 17+
- Maven 3.8+
- Kafka 3.0+
- SMTP server credentials for email notifications

# Build the application
mvn clean package

# Run the application
java -jar target/asteroid-alert-system.jar
```

## Usage

Once configured and running, the service will:
1. Periodically fetch NEO data from NASA
2. Process and analyze asteroid threats
3. Publish high-risk asteroid events to Kafka
4. Send email notifications to configured recipients

## License

MIT
