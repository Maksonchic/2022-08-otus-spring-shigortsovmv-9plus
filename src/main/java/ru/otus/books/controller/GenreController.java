package ru.otus.books.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.books.dto.GenreDto;
import ru.otus.books.repositories.GenreRepository;

@RestController
public class GenreController {

    private final GenreDtoService genreService;

    public GenreController(GenreDtoService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/v1/genres")
    public Flux<GenreDto> getGenres() {
        return repo.findAll().map(GenreDto::createDto);
    }
}
