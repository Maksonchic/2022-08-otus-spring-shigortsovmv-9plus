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
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = AuthorController.class)
@DisplayName("Авторы")
class TestAuthorController {

	@MockBean
	AuthorRepository authorRepository;

	@MockBean
	BookRepository bookRepository;

	@Autowired
	WebTestClient webClient;

	@Test
	@DisplayName("Сохранение")
	void testAuthorSave() {
		Author author = new Author("a2", "s", "", "", "1");

		when(authorRepository.save(any()))
				.then((Answer<Mono<Author>>) invocation -> Mono.just(author));

		webClient.post()
				.uri("/api/v1/authors")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(author))
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	@DisplayName("Получение всех")
	void testCounter() {
		Flux<Author> authorFlux = Flux.just(new Author("a1", "f", "", "", ""),
				new Author("a2", "s", "", "", "1"));

		when(authorRepository.findAll())
				.then((Answer<Flux<Author>>) invocation -> authorFlux);

		webClient.get()
				.uri("/api/v1/authors")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectStatus().isOk();
	}

	@Test
	@DisplayName("404")
	void testDirtyPath() {
		webClient.get()
				.uri("/api/v1/authors/notValid")
				.exchange()
				.expectStatus().isNotFound();
	}
}
