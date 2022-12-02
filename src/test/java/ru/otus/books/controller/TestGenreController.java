package ru.otus.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.books.models.Genre;
import ru.otus.books.repositories.GenreRepository;
import ru.otus.books.service.AuthorDtoServiceImpl;
import ru.otus.books.service.GenreDtoServiceImpl;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@Import(GenreDtoServiceImpl.class)
@DisplayName("Жанры")
class TestGenreController {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private GenreRepository repo;

	@Test
	@DisplayName("Получение")
	void testConvertEntityDto() throws Exception {
		List<Genre> genres = List.of(
				new Genre(1, "A"),
				new Genre(2, "B") );
		given(repo.findAll()).willReturn(genres);

		mvc.perform(get("/api/v1/genres"))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(genres)));
	}
}
