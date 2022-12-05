package ru.otus.books.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.books.dto.CommentDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "BOOKS")
public class Book {

    @Id
    @JsonProperty("bookId")
    private String id;
    private String title;
    @JsonProperty("page_count")
    private int pageCount;
    @JsonProperty("authorNickName")
    private String author;
    private Genre genre;
    private List<CommentDto> comments;

    public String getId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<CommentDto> getComments() {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pageCount=" + pageCount +
                ", author=" + author +
                ", genre=" + genre.getGenre() +
                ", comments=" + comments +
                '}';
    }
}

