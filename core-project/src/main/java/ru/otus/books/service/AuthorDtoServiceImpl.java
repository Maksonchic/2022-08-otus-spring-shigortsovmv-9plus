package ru.otus.books.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.dto.BookDto;
import ru.otus.books.models.Author;
import ru.otus.books.repositories.AuthorRepository;

import java.util.List;

@Service
public class AuthorDtoServiceImpl implements AuthorDtoService {

    private final AuthorRepository repo;

    public AuthorDtoServiceImpl(AuthorRepository repo) {
        this.repo = repo;
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> all = repo.findAll();
        return all.stream().map(AuthorDto::createDto).toList();
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAuthorBooks(String authorNickName) {
        Author author = repo.findByNickNameIgnoreCase(authorNickName);
        return author.getBooks().stream().map(BookDto::createDto).toList();
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @Override
    public AuthorDto add(String nickName, String lastName, String firstName, String middleName) {
        return AuthorDto.createDto(repo.save(new Author(0, nickName, lastName, firstName, middleName)));
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @Override
    @Transactional
    public void removeByNickName(String nickName) {
        repo.delete(repo.findByNickNameIgnoreCase(nickName));
    }
}
