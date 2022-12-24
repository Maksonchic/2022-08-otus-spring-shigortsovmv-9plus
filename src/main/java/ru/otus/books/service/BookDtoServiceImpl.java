package ru.otus.books.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.dto.BookDto;
import ru.otus.books.dto.CommentDto;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;
import ru.otus.books.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookDtoServiceImpl implements BookDtoService {

    private final BookRepository repo;
    private final AuthorRepository authorRepo;
    private final GenreRepository genreRepo;

    public BookDtoServiceImpl(BookRepository repo, AuthorRepository authorRepo, GenreRepository genreRepo) {
        this.repo = repo;
        this.authorRepo = authorRepo;
        this.genreRepo = genreRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(long id) {
        return BookDto.createDto(repo.findById(id), true);
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @Override
    @Transactional
    public BookDto add(String title, int page_count, String authorNickName, String genre) {
        Book book = new Book(
                0,
                title,
                page_count,
                authorRepo.findByNickNameIgnoreCase(authorNickName),
                genreRepo.findByGenreIgnoreCase(genre),
                new ArrayList<>()
        );
        return BookDto.createDto(repo.save(book), true);
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @Override
    public void removeBookById(long id) {
        repo.delete(repo.findById(id));
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @Override
    @Transactional
    public List<CommentDto> addBookComment(long bookId, String commentText) {
        Book book = repo.findById(bookId);
        Comment comment = new Comment(0, commentText);
        book.getComments().add(comment);
        return repo.save(book).getComments().stream().map(CommentDto::createDto).toList();
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getBookComments(long bookId) {
        return BookDto.createDto(repo.findById(bookId), false).getComments();
    }
}
