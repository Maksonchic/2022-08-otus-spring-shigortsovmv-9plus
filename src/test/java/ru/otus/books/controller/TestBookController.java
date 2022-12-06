package ru.otus.books.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.books.models.Author;
import ru.otus.books.models.Book;
import ru.otus.books.models.Genre;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;
import ru.otus.books.repositories.GenreRepository;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = BookController.class)
@DisplayName("Книги")
class TestBookController {

	@MockBean
	AuthorRepository authorRepository;

	@MockBean
	BookRepository bookRepository;

	@MockBean
	GenreRepository genreRepository;

	@Autowired
	WebTestClient webClient;

	@Test
	@DisplayName("Сохранение")
	void testAuthorSave() {
		Book book = new Book("asd", "ff", 12, "Michael", new Genre("1", ""), new ArrayList<>());

		when(authorRepository.findByNickNameIgnoreCase(any()))
				.then((Answer<Mono<Author>>) invocation -> Mono.just(
						new Author(book.getAuthor(), "Michael", "", "", "")));

		when(genreRepository.findByGenreIgnoreCase(any()))
				.then((Answer<Mono<Genre>>) invocation -> Mono.just(book.getGenre()));

		when(bookRepository.save(any()))
				.then((Answer<Mono<Book>>) invocation -> Mono.just(book));

		webClient.post()
				.uri("/api/v1/books")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(book))
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	@DisplayName("Получение всех")
	void testCounter() {
		Flux<Book> bookFlux = Flux.just(new Book("asd2", "", 123, "s2f", new Genre("1", ""), new ArrayList<>()),
				new Book("asd", "", 12, "s2f", new Genre("1", ""), new ArrayList<>()));

		when(authorRepository.findByNickNameIgnoreCase(any()))
				.then((Answer<Mono<Author>>) invocation -> Mono.just(
						new Author("s2f", "Michael", "", "", "")));

		when(bookRepository.findAllByAuthor(any()))
				.then((Answer<Flux<Book>>) invocation -> bookFlux);

		webClient.get()
				.uri("/api/v1/books/author/Michael")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectStatus().isOk();
	}
}
