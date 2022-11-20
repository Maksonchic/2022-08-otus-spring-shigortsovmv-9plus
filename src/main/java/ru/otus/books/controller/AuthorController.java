package ru.otus.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.service.AuthorDtoService;

import java.util.List;

@CrossOrigin
@RestController
public class AuthorController {

    @Autowired
    AuthorDtoService authorService;

    @GetMapping("/api/v1/authors")
    public List<AuthorDto> getBooks() {
        return authorService.getAllAuthors();
    }

    @DeleteMapping("/api/v1/authors")
    public void removeAuthor(@RequestParam("authorNickName") String authorNickName) {
        authorService.removeByNickName(authorNickName);
    }

    @PostMapping("/api/v1/authors")
    public AuthorDto addBook(
            @RequestParam("nickName") String nickName,
            @RequestParam("lastName") String lastName,
            @RequestParam("firstName") String firstName,
            @RequestParam("middleName") String middleName) {
        return authorService.add(nickName, lastName, firstName, middleName);
    }
}
