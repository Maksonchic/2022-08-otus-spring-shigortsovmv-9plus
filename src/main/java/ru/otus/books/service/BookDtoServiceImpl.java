package ru.otus.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.dto.BookDto;
import ru.otus.books.models.Author;
import ru.otus.books.models.Book;
import ru.otus.books.dto.CommentDto;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;
import ru.otus.books.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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
        return BookDto.createDto(repo.findById(id).orElseThrow(), true);
    }

    @Override
    @Transactional
    public BookDto add(String title, int page_count, String authorNickName, String genre) {
        Author author = authorRepo.findByNickNameIgnoreCase(authorNickName);
        Book book = new Book(
                0,
                title,
                page_count,
                author.getId(),
                genreRepo.findByGenreIgnoreCase(genre),
                new ArrayList<>());

        saveBoth(author, book);

        return BookDto.createDto(book);
    }

    @Override
    @Transactional
    public void removeBookById(long id) {
        Book book = repo.findById(id).orElseThrow();
        updateAuthorRemoveBook(book);
        removeCommentsFromBook(book);
        repo.delete(book);
    }

    @Override
    @Transactional
    public List<CommentDto> addBookComment(long bookId, String commentText) {
        CommentDto comment = new CommentDto(UUID.randomUUID().toString(), commentText);
        Book book = repo.findById(bookId).orElseThrow();
        book.getComments().add(comment);
        return repo.save(book).getComments();
    }

    @Override
    public List<CommentDto> getBookComments(long bookId) {
        Book book = repo.findById(bookId).orElseThrow();
        return book.getComments();
    }

    @Override
    public void removeComment(String commentId) {
        Book book = repo.findByCommentsId(commentId);
        book.getComments().removeIf(c -> c.id().equals(commentId));
        repo.save(book);
    }

    @Override
    public void editComment(String commentId, String newCommentText) {
        Book book = repo.findByCommentsId(commentId);
        List<CommentDto> validOldComments = book.getComments().stream().filter(c -> !c.id().equals(commentId)).toList();
        validOldComments.add(new CommentDto(commentId, newCommentText));
        repo.save(book);
    }

    private void saveBoth(Author author, Book book) {
        book = repo.save(book);
        author.getBooks().add(book);
        authorRepo.save(author);
    }

    private void removeCommentsFromBook(Book book) {
        book.setComments(new ArrayList<>());
        repo.save(book);
    }

    private void updateAuthorRemoveBook(Book book) {
        Author author = authorRepo.findById(book.getAuthor()).orElseThrow();
        author.getBooks().removeIf(b -> b.equals(book.getId()));
        authorRepo.save(author);
    }
}
