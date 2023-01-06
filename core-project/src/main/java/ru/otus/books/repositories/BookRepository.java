package ru.otus.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.books.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findById(final long bookId);
}
