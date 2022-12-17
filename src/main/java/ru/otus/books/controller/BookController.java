package ru.otus.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.books.dto.BookDto;
import ru.otus.books.dto.CommentDto;
import ru.otus.books.service.AuthorDtoService;
import ru.otus.books.service.BookDtoService;
import ru.otus.books.service.CommentDtoService;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    AuthorDtoService authorService;

    @Autowired
    BookDtoService bookService;

    @Autowired
    CommentDtoService commentService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/api/v1/books/author/{authorNickName}")
    public List<BookDto> getBooks(@PathVariable String authorNickName) {
        return authorService.getAuthorBooks(authorNickName);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/api/v1/comments/{bookId}")
    public List<CommentDto> getBookComments(@PathVariable long bookId) {
        return bookService.getBookComments(bookId);
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @DeleteMapping("/api/v1/books")
    public void removeBook(@RequestParam("bookId") long bookId) {
        bookService.removeBookById(bookId);
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @PostMapping("/api/v1/books")
    public BookDto addBook(
            @RequestParam("title") String title,
            @RequestParam("page_count") int page_count,
            @RequestParam("authorNickName") String authorNickName,
            @RequestParam("genre") String genre) {
        return bookService.add(title, page_count, authorNickName, genre);
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @DeleteMapping("/api/v1/comments")
    public void removeBookComment(@RequestParam("commentId") long commentId) {
        commentService.removeComment(commentId);
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @PostMapping("/api/v1/comments")
    public List<CommentDto> addBookComment(
            @RequestParam("bookId") long bookId,
            @RequestParam("message") String message) {
        return bookService.addBookComment(bookId, message);
    }
}
