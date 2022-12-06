package ru.otus.books.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.books.models.Genre;
import ru.otus.books.repositories.GenreRepository;

import static org.mockito.Mockito.when;

@WebFluxTest(GenreController.class)
@DisplayName("Жанры")
class TestGenreController {

	@MockBean
	private GenreRepository genreRepository;

	@Autowired
	WebTestClient webClient;

	@Test
	@DisplayName("Получение")
	void testGetAll() {
		Flux<Genre> genreMono = Flux.just(new Genre("s2f", "qwe"), new Genre("s2f2", "qwe3"));

		when(genreRepository.findAll())
				.then((Answer<Flux<Genre>>) invocation -> genreMono);

		webClient.get()
				.uri("/api/v1/genres")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectStatus().isOk();
	}
}
