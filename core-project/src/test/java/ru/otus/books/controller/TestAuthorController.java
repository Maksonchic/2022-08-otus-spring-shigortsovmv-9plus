package ru.otus.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.books.models.Author;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.service.AuthorDtoServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@Import(AuthorDtoServiceImpl.class)
@DisplayName("Авторы")
class TestAuthorController {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private AuthorRepository repo;

	@Autowired
	private WebApplicationContext context;

	@Test
	@WithMockUser(
			username = "USER",
			authorities = "RONIN"
	)
	@DisplayName("Получение всех")
	public void getAllAuthors() throws Exception {
		List<Author> authors = List.of(
				new Author(1, "Michael", "Last", "Firstov", "Middleich", new ArrayList<>()),
				new Author(2, "qwe", "qweFN", "qweLN", "qweMN", new ArrayList<>()) );
		given(repo.findAll()).willReturn(authors);

		mvc.perform(get("/api/v1/authors"))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(authors)));
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

		mvc.perform(post("/api/v1/authors")
						.content(String.format("nickName=%s&lastName=%s&firstName=%s&middleName=%s"
								, author.getNickName()
								, author.getLastName()
								, author.getFirstName()
								, author.getMiddleName()))
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	@DisplayName("Удаление")
	public void deleteAuthor() throws Exception {
		mvc.perform(delete("/api/v1/authors")
						.content("authorNickName=Michael"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Ошибка")
	public void deleteAuthorError() throws Exception {
		mvc.perform(delete("/api/v1/authors")
						.content("authorNickNam=Michael")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().is4xxClientError());
	}
}
