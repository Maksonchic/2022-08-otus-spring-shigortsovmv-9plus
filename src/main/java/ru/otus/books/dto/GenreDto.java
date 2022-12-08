package ru.otus.books.dto;

import lombok.EqualsAndHashCode;
import ru.otus.books.models.Genre;

@EqualsAndHashCode
public class GenreDto {
    private final String id;
    private final String genre;

    public GenreDto(String id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public GenreDto(Genre genre) {
        this.id = genre.getId();
        this.genre = genre.getGenre();
    }

    public static GenreDto createDto(Genre genre) {
        return new GenreDto(genre);
    }

    public static Genre createEntity(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getGenre());
    }

    public String getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"genre\":\"" + genre + "\"" +
                "}";
    }
}
