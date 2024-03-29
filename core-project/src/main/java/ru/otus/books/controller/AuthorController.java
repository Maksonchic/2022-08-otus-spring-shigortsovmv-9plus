package ru.otus.books.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.service.AuthorDtoService;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorDtoService authorService;

    public AuthorController(AuthorDtoService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/v1/authors")
    public List<AuthorDto> getAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping("/api/v1/authors")
    public AuthorDto addAuthor(
            @RequestParam("nickName") String nickName,
            @RequestParam("lastName") String lastName,
            @RequestParam("firstName") String firstName,
            @RequestParam("middleName") String middleName) {
        return authorService.add(nickName, lastName, firstName, middleName);
    }

    @DeleteMapping("/api/v1/authors")
    public void removeAuthor(@RequestParam("authorNickName") String authorNickName) {
        authorService.removeByNickName(authorNickName);
    }
}
