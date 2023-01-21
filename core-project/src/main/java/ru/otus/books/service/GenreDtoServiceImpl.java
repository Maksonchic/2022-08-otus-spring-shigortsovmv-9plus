package ru.otus.books.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.dto.GenreDto;
import ru.otus.books.repositories.GenreRepository;

import java.util.List;

@Service
public class GenreDtoServiceImpl implements GenreDtoService {

    private final GenreRepository repo;

    public GenreDtoServiceImpl(GenreRepository repo) {
        this.repo = repo;
    }

    // allow all authorized users
    @Override
    public List<GenreDto> getAllGenres() {
        return repo.findAll().stream().map(GenreDto::createDto).toList();
    }

    @Override
    public void add(String name) {
        repo.save(GenreDto.createEntity(new GenreDto(0, name)));
    }

    @Override
    @Transactional
    public void removeGenre(String genre) {
        repo.deleteByGenreIgnoreCase(genre);
    }

    @Override
    public GenreDto getByGenre(String genre) {
        return GenreDto.createDto(repo.findByGenreIgnoreCase(genre));
    }
}
