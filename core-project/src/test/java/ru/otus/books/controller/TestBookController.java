package ru.otus.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import ru.otus.books.repositories.CommentRepository;
import ru.otus.books.repositories.GenreRepository;
import ru.otus.books.service.AuthorDtoService;
import ru.otus.books.service.AuthorDtoServiceImpl;
import ru.otus.books.service.BookDtoService;
import ru.otus.books.service.BookDtoServiceImpl;
import ru.otus.books.service.CommentDtoService;
import ru.otus.books.service.CommentDtoServiceImpl;
import ru.otus.books.service.GenreDtoServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import({BookDtoServiceImpl.class, AuthorDtoServiceImpl.class, CommentDtoServiceImpl.class})
@DisplayName("Книги")
class TestBookController {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private BookRepository repo;

	@MockBean
	private AuthorRepository authorRepo;

	@MockBean
	private CommentRepository commentRepo;

	@MockBean
	private GenreRepository genreRepo;

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
