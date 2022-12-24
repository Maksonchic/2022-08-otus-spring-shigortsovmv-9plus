package ru.otus.books.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.dto.BookDto;
import ru.otus.books.dto.GenreDto;
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
	@WithMockUser
	@DisplayName("Получаем всех Михаеля")
	public void checkFind() throws Exception {
		List<Book> books = List.of(
				new Book(0, "New one", 404, null, new Genre(0, "a"), new ArrayList<>()),
				new Book(1, "New second", 302, null, new Genre(0, "a"), new ArrayList<>()),
				new Book(2, "Three one", 500,
						new Author(0, "a", "b", "c", "d", List.of(
								new Book(1, "New second", 302, null,
										new Genre(0, "a"), new ArrayList<>()))),
						new Genre(0, "a"), new ArrayList<>())
		);
		Author author = new Author(0, "a", "b", "c", "d", books);

		given(authorRepo.findByNickNameIgnoreCase("michael")).willReturn(author);

		System.out.println(mapper.writeValueAsString(books));

		books.get(2).setAuthor(null);

		mvc.perform(get("/api/v1/books/author/michael"))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(books)));
	}
}
