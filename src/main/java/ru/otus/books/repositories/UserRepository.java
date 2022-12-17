package ru.otus.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.books.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(final String userName);
}
