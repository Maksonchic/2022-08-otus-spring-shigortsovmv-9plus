package ru.otus.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.books.dto.GenreDto;
import ru.otus.books.repositories.GenreRepository;

@RestController
public class GenreController {

    @Autowired
    GenreRepository repo;

    @CrossOrigin
    @GetMapping("/api/v1/genres")
    public Flux<GenreDto> getGenres() {
        return repo.findAll().map(GenreDto::createDto);
    }
}
