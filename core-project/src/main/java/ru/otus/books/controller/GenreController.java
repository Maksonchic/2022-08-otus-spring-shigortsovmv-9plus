package ru.otus.books.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.books.dto.GenreDto;
import ru.otus.books.service.GenreDtoService;

import java.util.List;

@RestController
public class GenreController {

    private final GenreDtoService genreService;

    public GenreController(GenreDtoService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/v1/genres")
    public List<GenreDto> getGenres() {
        return genreService.getAllGenres();
    }
}
