package ru.otus.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.books.dto.GenreDto;
import ru.otus.books.repositories.GenreRepository;

import java.util.List;

@Service
public class GenreDtoServiceImpl implements GenreDtoService {

    @Autowired
    GenreRepository repo;

    @Override
    public List<GenreDto> getAllGenres() {
        return repo.findAll().stream().map(GenreDto::createDto).toList();
    }

    @Override
    public void add(String name) {
        repo.save(GenreDto.createEntity(new GenreDto(0, name)));
    }
}
