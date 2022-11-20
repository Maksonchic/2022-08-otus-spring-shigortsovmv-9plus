package ru.otus.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.books.dto.GenreDto;
import ru.otus.books.service.AuthorDtoService;
import ru.otus.books.service.BookDtoService;
import ru.otus.books.service.CommentDtoService;
import ru.otus.books.service.GenreDtoService;

import java.util.List;

@RestController
public class GenreController {

    @Autowired
    AuthorDtoService authorService;

    @Autowired
    BookDtoService bookService;

    @Autowired
    GenreDtoService genreService;

    @Autowired
    CommentDtoService commentService;

    @CrossOrigin
    @GetMapping("/api/v1/genres")
    public List<GenreDto> getGenres() {
        return genreService.getAllGenres();
    }
}
