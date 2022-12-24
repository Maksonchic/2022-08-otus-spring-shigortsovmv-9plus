package ru.otus.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.books.models.Author;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@Import(AuthorDtoServiceImpl.class)
@DisplayName("Авторы")
class TestAuthorController {

	@MockBean
	AuthorRepository authorRepository;

	@MockBean
	BookRepository bookRepository;

	@Autowired
	WebTestClient webClient;

	@Autowired
	private WebApplicationContext context;

	@Test
	@WithMockUser
	@DisplayName("Получение всех")
	public void getAllAuthors() throws Exception {
		List<Author> authors = List.of(
				new Author(1, "Michael", "Last", "Firstov", "Middleich", new ArrayList<>()),
				new Author(2, "qwe", "qweFN", "qweLN", "qweMN", new ArrayList<>()) );
		given(repo.findAll()).willReturn(authors);

		webClient.post()
				.uri("/api/v1/authors")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(author))
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	@WithMockUser
	@DisplayName("Добавление")
	public void insertAuthor() throws Exception {
		Author author = new Author(
				0,
				"Michael",
				"Last",
				"Firstov",
				"Middleich",
				new ArrayList<>());
		given(repo.save(author)).willReturn(author);

		when(authorRepository.findAll())
				.then((Answer<Flux<Author>>) invocation -> authorFlux);

	@Test
	@WithMockUser
	@DisplayName("Удаление")
	public void deleteAuthor() throws Exception {
		mvc.perform(delete("/api/v1/authors")
						.content("authorNickName=Michael"))
				.andExpect(status().isOk());
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
