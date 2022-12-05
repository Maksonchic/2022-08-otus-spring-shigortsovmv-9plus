package ru.otus.books.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.books.models.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, Long> {
    Mono<Genre> findByGenreIgnoreCase(final String genre);
}
