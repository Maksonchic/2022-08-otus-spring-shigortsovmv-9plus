package ru.otus.books.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.books.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Mono<Book> findByCommentsId(String commentId);
    Mono<Book> save(final Book book);
    Flux<Book> findAllByAuthor(final String author);
    Mono<Void> deleteAllByAuthor(final String author);
}
