package ru.otus.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.dto.BookDto;
import ru.otus.books.models.Author;
import ru.otus.books.repositories.AuthorRepository;

import java.util.List;

@Service
public class AuthorDtoServiceImpl implements AuthorDtoService {

    @Autowired
    private AuthorRepository repo;

    @Override
    public List<AuthorDto> getAllAuthors() {
        return repo.findAll().stream().map(AuthorDto::createDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAuthorBooks(String authorNickName) {
        Author author = repo.findByNickNameIgnoreCase(authorNickName);
        return author.getBooks().stream().map(BookDto::createDto).toList();
    }

    @Override
    public void add(String nickName, String lastName, String firstName, String middleName) {
        repo.save(new Author(0, nickName, lastName, firstName, middleName));
    }

    @Override
    @Transactional
    public void removeByNickName(String nickName) {
        repo.delete(repo.findByNickNameIgnoreCase(nickName));
    }
}
