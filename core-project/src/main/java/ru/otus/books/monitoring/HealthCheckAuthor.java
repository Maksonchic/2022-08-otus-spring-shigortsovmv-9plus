package ru.otus.books.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.books.models.Author;
import ru.otus.books.repositories.AuthorRepository;

@Component
public class HealthCheckAuthor implements HealthIndicator {

    private final AuthorRepository authorRepository;

    public HealthCheckAuthor(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Health health() {
        for (Author a : authorRepository.findAll()) {
            if (a.getAge() > 80) {
                return Health.down()
                        .status(Status.DOWN)
                        .withDetail("message", "Кое-кто много книжек уже не напишет :)")
                        .build();
            }
        }
        return Health
                .up()
                .withDetail("message", "Всё окей")
                .build();
    }
}
