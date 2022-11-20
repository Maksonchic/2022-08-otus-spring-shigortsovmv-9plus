package ru.otus.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.dto.BookDto;
import ru.otus.books.dto.CommentDto;
import ru.otus.books.dto.GenreDto;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;
import ru.otus.books.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookDtoServiceImpl implements BookDtoService {

    @Autowired
    BookRepository repo;

    @Autowired
    AuthorRepository authorRepo;

    @Autowired
    GenreRepository genreRepo;

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(long id) {
        return BookDto.createDto(repo.findById(id), true);
    }

    @Override
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

    @Override
    public void removeBookById(long id) {
        repo.delete(repo.findById(id));
    }

    @Override
    @Transactional
    public List<CommentDto> addBookComment(long bookId, String commentText) {
        Book book = repo.findById(bookId);
        Comment comment = new Comment(0, commentText);
        book.getComments().add(comment);
        return repo.save(book).getComments().stream().map(CommentDto::createDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getBookComments(long bookId) {
        return BookDto.createDto(repo.findById(bookId), false).getComments();
    }
}
