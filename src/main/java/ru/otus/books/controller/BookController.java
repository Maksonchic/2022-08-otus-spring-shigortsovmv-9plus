package ru.otus.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.books.dto.BookDto;
import ru.otus.books.dto.CommentDto;
import ru.otus.books.models.Book;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;
import ru.otus.books.repositories.GenreRepository;

import java.util.UUID;

@CrossOrigin
@RestController
public class BookController {

    @Autowired
    BookRepository repo;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/api/v1/books/author/{authorNickName}")
    public Flux<BookDto> getBooks(@PathVariable String authorNickName) {
        return authorRepository
                .findByNickNameIgnoreCase(authorNickName)
                .flatMapMany(a -> repo.findAllByAuthor(a.getId()))
                .map(BookDto::createDto);
    }

    @GetMapping("/api/v1/comments/{bookId}")
    public Flux<CommentDto> getBookComments(@PathVariable String bookId) {
        return repo
                .findById(bookId)
                .flatMapIterable(Book::getComments);
    }

    @DeleteMapping("/api/v1/books")
    public Mono<Void> removeBook(@RequestBody Book book) {
        return repo.delete(book);
    }

    @PostMapping("/api/v1/books")
    public Mono<BookDto> addBook(@RequestBody Book book) {
        return Mono.zip(
                authorRepository.findByNickNameIgnoreCase(book.getAuthor()),
                genreRepository.findByGenreIgnoreCase(book.getGenre().getGenre())
        ).flatMap(res -> {
            book.setAuthor(res.getT1().getId());
            book.setGenre(res.getT2());
            return repo.save(book);
        }).map(BookDto::createDto);
    }

    @DeleteMapping("/api/v1/comments")
    public Flux<CommentDto> removeBookComment(@RequestBody CommentDto commentDto) {
        return repo.findByCommentsId(commentDto.id())
                .flatMap(b -> {
                    b.setComments(b.getComments().stream().filter(c -> !c.id().equals(commentDto.id())).toList());
                    return repo.save(b);
                })
                .map(BookDto::createDto)
                .flatMapIterable(BookDto::getComments);
    }

    @PostMapping("/api/v1/comments")
    public Flux<CommentDto> addBookComment(@RequestBody Book book) {
        return repo
                .findById(book.getId())
                .map(b -> {
                    b.getComments()
                            .addAll(
                                    book.getComments()
                                            .stream()
                                            .map(c -> new CommentDto(UUID.randomUUID().toString(), c.message())).toList());
                    return b;
                })
                .flatMap(repo::save)
                .flatMapIterable(Book::getComments);
    }
}
