package ru.otus.books.dto;

import lombok.EqualsAndHashCode;
import ru.otus.books.models.Author;
import ru.otus.books.models.Book;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class AuthorDto {

    private final String id;
    private final String nickName;
    private final String lastName;
    private final String firstName;
    private final String middleName;

    public AuthorDto(String id, String nickName, String lastName, String firstName, String middleName) {
        this.id = id;
        this.nickName = nickName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public static AuthorDto createDto(final Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorDto(
                author.getId(),
                author.getNickName(),
                author.getLastName(),
                author.getFirstName(),
                author.getMiddleName());
    }

    public static Author createEntity(AuthorDto authorDto) {
        return new Author(
                authorDto.getId(),
                authorDto.getNickName(),
                authorDto.getLastName(),
                authorDto.getFirstName(),
                authorDto.getMiddleName());
    }

    public String getId() {
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

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"nickName\":\"" + nickName + "\"" +
                ",\"lastName\":\"" + lastName + "\"" +
                ",\"firstName\":\"" + firstName + "\"" +
                ",\"middleName\":\"" + middleName + "\"" +
                "}";
    }
}
