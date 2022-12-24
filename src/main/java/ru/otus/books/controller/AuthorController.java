package ru.otus.books.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.models.Author;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;

import java.time.Duration;

@RestController
public class AuthorController {

    private final AuthorDtoService authorService;

    public AuthorController(AuthorDtoService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/v1/authors")
    public Flux<AuthorDto> getAuthors() {
        return repo.findAll().map(AuthorDto::createDto);
    }

    @PostMapping("/api/v1/authors")
    public Mono<AuthorDto> addAuthor(@RequestBody Author author) {
        return repo.save(author).map(AuthorDto::createDto);
    }

    @DeleteMapping("/api/v1/authors")
    public Mono<Void> removeAuthor(@RequestBody Author author) {
        return repo.findByNickNameIgnoreCase(author.getNickName())
                .flatMap(a -> bookRepository.deleteAllByAuthor(a.getId()))
                .then(repo.deleteByNickName(author.getNickName()));
    }
}
