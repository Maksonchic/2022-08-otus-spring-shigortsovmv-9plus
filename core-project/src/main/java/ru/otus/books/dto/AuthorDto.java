package ru.otus.books.dto;

import lombok.EqualsAndHashCode;
import ru.otus.books.models.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class AuthorDto {

    private final long id;
    private final String nickName;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final List<BookDto> books;

    public AuthorDto(long id, String nickName, String lastName, String firstName, String middleName, List<BookDto> books) {
        this.id = id;
        this.nickName = nickName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.books = books;
    }

    // return author without books
    public static AuthorDto createDto(final Author author) {
        return AuthorDto.createDto(author, false);
    }

    public static AuthorDto createDto(final Author author, boolean isBooksContains) {
        if (author == null) {
            return null;
        }
        if (!isBooksContains) {
            author.setBooks(new ArrayList<>());
        }
        return new AuthorDto(
                author.getId(),
                author.getNickName(),
                author.getLastName(),
                author.getFirstName(),
                author.getMiddleName(),
                author.getBooks().stream().map(b -> BookDto.createDto(b, false)).collect(Collectors.toList()));
    }

    public static Author createEntity(AuthorDto authorDto) {
        return new Author(
                authorDto.getId(),
                authorDto.getNickName(),
                authorDto.getLastName(),
                authorDto.getFirstName(),
                authorDto.getMiddleName(),
<<<<<<< HEAD
                authorDto.getBooks().stream().map(BookDto::createEntity).collect(Collectors.toList()));
=======
                new Random().nextInt(100),
                authorDto.getBooks().stream().map(BookDto::createEntity).toList());
>>>>>>> f88f688e1a2cb839f78b88a72d6e312f6fe66726
    }

    public long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public List<BookDto> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"nickName\":\"" + nickName + "\"" +
                ",\"lastName\":\"" + lastName + "\"" +
                ",\"firstName\":\"" + firstName + "\"" +
                ",\"middleName\":\"" + middleName + "\"" +
                ",\"books\":" + books +
                "}";
    }
}
