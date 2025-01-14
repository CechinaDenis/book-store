package com.code4fun.book.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.code4fun.book.BaseIntegrationTest;
import com.code4fun.book.FileToStringConverter;
import com.code4fun.book.repository.AuthorRepository;
import com.code4fun.book.repository.BookRepository;
import com.code4fun.book.repository.CategoryRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

public class BookControllerIT extends BaseIntegrationTest {

  @Autowired BookRepository bookRepository;
  @Autowired AuthorRepository authorRepository;
  @Autowired CategoryRepository categoryRepository;

  @Test
  @WithMockUser
  @DatabaseSetup("bookController.xml")
  void shouldReturnBook_whenIdIsValid() throws Exception {
    mockMvc
        .perform(get("/api/books/{id}", "book-1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("book-1"))
        .andExpect(jsonPath("$.name").value("book-1"))
        .andExpect(jsonPath("$.reading").value("reading-1"))
        .andExpect(jsonPath("$.series").value("series-1"))
        .andExpect(jsonPath("$.year").value(2000))
        .andExpect(jsonPath("$.duration").value(480))
        .andExpect(jsonPath("$.quality").value(128))
        .andExpect(jsonPath("$.authorIds[0]").value("author-1"))
        .andExpect(jsonPath("$.categoryIds[0]").value("category-1"));
  }

  @Test
  @WithMockUser
  @DatabaseSetup("bookController.xml")
  void shouldReturnNotFound_whenIdIsInvalid() throws Exception {
    mockMvc
        .perform(get("/api/books/{id}", "invalid-book"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$").value("Book with id: 'invalid-book' was not found."));
  }

  @Test
  @WithMockUser
  @DatabaseSetup("bookController.xml")
  void shouldReturnBadRequest_whenIdIsBlank() throws Exception {
    mockMvc
        .perform(get("/api/books/{id}", "   "))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$").value("findById.id: must not be blank"));
  }

  @Test
  @WithMockUser
  @DatabaseSetup("bookController.xml")
  void shouldReturnPageOfBooks_whenFindAll() throws Exception {
    mockMvc
        .perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    FileToStringConverter.getContent(
                        PATH_TO_RESPONSE + "/findAllBooksResponse.json")));
  }

  @Test
  @WithMockUser
  @Transactional
  @DatabaseSetup("bookController.xml")
  void shouldReturnCreatedBook_whenPayloadIsValid() throws Exception {
    mockMvc
        .perform(
            post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                          "name": "book-new",
                          "reading": "reader-new",
                          "series": "series-new",
                          "year": 2000,
                          "duration": 521,
                          "quality": 192
                        }
                        """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.name").value("book-new"))
        .andExpect(jsonPath("$.reading").value("reader-new"))
        .andExpect(jsonPath("$.series").value("series-new"))
        .andExpect(jsonPath("$.year").value(2000))
        .andExpect(jsonPath("$.duration").value(521))
        .andExpect(jsonPath("$.quality").value(192))
        .andExpect(jsonPath("$.authorIds").isEmpty())
        .andExpect(jsonPath("$.categoryIds").isEmpty());

    final var book = bookRepository.findBookByName("book-new").get();
    assertThat(book.getName()).isEqualTo("book-new");
    assertThat(book.getReading()).isEqualTo("reader-new");
    assertThat(book.getSeries()).isEqualTo("series-new");
    assertThat(book.getYear()).isEqualTo(2000);
    assertThat(book.getDuration()).isEqualTo(521);
    assertThat(book.getQuality()).isEqualTo(192);
    assertThat(book.getAuthors()).isEmpty();
    assertThat(book.getCategories()).isEmpty();
  }

  @Test
  @WithMockUser
  @Transactional
  @DatabaseSetup("bookController.xml")
  void shouldReturnUpdatedBook_whenRequestIsValid() throws Exception {
    mockMvc
        .perform(
            put("/api/books/{id}", "book-2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                {
                  "name": "book-edited",
                  "reading": "reader-edited",
                  "series": "series-edited",
                  "year": 2022,
                  "duration": 222,
                  "quality": 193
                }
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("book-2"))
        .andExpect(jsonPath("$.name").value("book-edited"))
        .andExpect(jsonPath("$.reading").value("reader-edited"))
        .andExpect(jsonPath("$.series").value("series-edited"))
        .andExpect(jsonPath("$.year").value(2022))
        .andExpect(jsonPath("$.duration").value(222))
        .andExpect(jsonPath("$.quality").value(193))
        .andExpect(jsonPath("$.authorIds").isEmpty())
        .andExpect(jsonPath("$.categoryIds").isEmpty());

    final var book = bookRepository.findById("book-2").get();
    assertThat(book.getName()).isEqualTo("book-edited");
    assertThat(book.getReading()).isEqualTo("reader-edited");
    assertThat(book.getSeries()).isEqualTo("series-edited");
    assertThat(book.getYear()).isEqualTo(2022);
    assertThat(book.getDuration()).isEqualTo(222);
    assertThat(book.getQuality()).isEqualTo(193);
    assertThat(book.getAuthors()).isEmpty();
    assertThat(book.getCategories()).isEmpty();
  }

  @Test
  @WithMockUser
  @Transactional
  @DatabaseSetup("bookController.xml")
  void shouldReturnOK_whenBookIsDeletedAndRequestRequestIsValid() throws Exception {
    mockMvc.perform(delete("/api/books/{id}", "book-1")).andExpect(status().isOk());

    var book = bookRepository.findById("book-1");
    assertThatThrownBy(() -> book.get()).isInstanceOf(NoSuchElementException.class);

    var author = authorRepository.findById("author-1").get();
    assertThat(author.getBooks()).isEmpty();

    var category = categoryRepository.findById("category-1").get();
    assertThat(category.getBooks()).isEmpty();
  }

  @Test
  @WithMockUser
  @Transactional
  @DatabaseSetup("bookController.xml")
  void shouldReturnBookWithAddedAuthor_whenIdsAreValid() throws Exception {
    mockMvc
        .perform(post("/api/books/{bookId}/authors/{authorId}", "book-2", "author-2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("book-2"))
        .andExpect(jsonPath("$.name").value("book-2"))
        .andExpect(jsonPath("$.reading").value("reading-1"))
        .andExpect(jsonPath("$.series").value("series-1"))
        .andExpect(jsonPath("$.year").value(2001))
        .andExpect(jsonPath("$.duration").value(490))
        .andExpect(jsonPath("$.quality").value(128))
        .andExpect(jsonPath("$.authorIds").isArray())
        .andExpect(jsonPath("$.authorIds.length()").value(1))
        .andExpect(jsonPath("$.authorIds[0]").value("author-2"))
        .andExpect(jsonPath("$.categoryIds").isEmpty());

    final var book = bookRepository.findById("book-2").get();
    assertThat(book.getId()).isEqualTo("book-2");
    assertThat(book.getName()).isEqualTo("book-2");
    assertThat(book.getReading()).isEqualTo("reading-1");
    assertThat(book.getSeries()).isEqualTo("series-1");
    assertThat(book.getYear()).isEqualTo(2001);
    assertThat(book.getDuration()).isEqualTo(490);
    assertThat(book.getQuality()).isEqualTo(128);
    assertThat(book.getAuthors().size()).isEqualTo(1);
    assertThat(book.getAuthors().stream().toList().get(0).getId()).isEqualTo("author-2");
    assertThat(book.getAuthors().stream().toList().get(0).getFirstName())
        .isEqualTo("first-name-author-2");
    assertThat(book.getAuthors().stream().toList().get(0).getLastName())
        .isEqualTo("last-name-author-2");
    assertThat(book.getCategories().size()).isEqualTo(0);

    final var authorsBooks =
        authorRepository.findById("author-2").get().getBooks().stream().toList();
    assertThat(authorsBooks.size()).isEqualTo(1);
    assertThat(authorsBooks.get(0).getId()).isEqualTo("book-2");
  }

  @ParameterizedTest
  @MethodSource("provideStringsForIsBlank")
  @WithMockUser
  @DatabaseSetup("bookController.xml")
  void shouldReturnBadRequest_whenOneOfIdsAreBlankForAddAuthor(String bookId, String authorId)
      throws Exception {
    mockMvc
        .perform(post("/api/books/{bookId}/authors/{authorId}", bookId, authorId))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser
  @Transactional
  @DatabaseSetup("bookController.xml")
  void shouldReturnBookWithAddedCategory_whenIdsAreValid() throws Exception {
    mockMvc
        .perform(post("/api/books/{bookId}/categories/{categoryId}", "book-2", "category-1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("book-2"))
        .andExpect(jsonPath("$.name").value("book-2"))
        .andExpect(jsonPath("$.reading").value("reading-1"))
        .andExpect(jsonPath("$.series").value("series-1"))
        .andExpect(jsonPath("$.year").value(2001))
        .andExpect(jsonPath("$.duration").value(490))
        .andExpect(jsonPath("$.quality").value(128))
        .andExpect(jsonPath("$.categoryIds").isArray())
        .andExpect(jsonPath("$.categoryIds.length()").value(1))
        .andExpect(jsonPath("$.categoryIds[0]").value("category-1"))
        .andExpect(jsonPath("$.authorIds").isEmpty());

    final var book = bookRepository.findById("book-2").get();
    assertThat(book.getId()).isEqualTo("book-2");
    assertThat(book.getName()).isEqualTo("book-2");
    assertThat(book.getReading()).isEqualTo("reading-1");
    assertThat(book.getSeries()).isEqualTo("series-1");
    assertThat(book.getYear()).isEqualTo(2001);
    assertThat(book.getDuration()).isEqualTo(490);
    assertThat(book.getQuality()).isEqualTo(128);

    final var categories = book.getCategories().stream().toList();
    assertThat(categories.size()).isEqualTo(1);
    assertThat(categories.get(0).getId()).isEqualTo("category-1");
    assertThat(categories.get(0).getName()).isEqualTo("category-1");
  }

  @ParameterizedTest
  @MethodSource("provideStringsForIsBlank")
  @WithMockUser
  @DatabaseSetup("bookController.xml")
  void shouldReturnBadRequest_whenOneOfIdsAreBlankForAddCategory(String bookId, String categoryId)
      throws Exception {
    mockMvc
        .perform(post("/api/books/{bookId}/categories/{categoryId}", bookId, categoryId))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser
  @Transactional
  @DatabaseSetup("bookController.xml")
  void shouldReturnOk_whenRemoveAuthorFromBook() throws Exception {
    mockMvc
        .perform(delete("/api/books/{bookId}/authors/{authorId}", "book-1", "author-1"))
        .andExpect(status().isOk());

    final var book = bookRepository.findById("book-1").get();
    assertThat(book.getId()).isEqualTo("book-1");
    assertThat(book.getName()).isEqualTo("book-1");
    assertThat(book.getReading()).isEqualTo("reading-1");
    assertThat(book.getSeries()).isEqualTo("series-1");
    assertThat(book.getYear()).isEqualTo(2000);
    assertThat(book.getDuration()).isEqualTo(480);
    assertThat(book.getQuality()).isEqualTo(128);
    assertThat(book.getAuthors().size()).isEqualTo(0);
    assertThat(book.getCategories().size()).isEqualTo(1);

    final var authorsBooks = authorRepository.findById("author-1").get().getBooks();
    assertThat(authorsBooks.size()).isEqualTo(0);
  }

  @ParameterizedTest
  @MethodSource("provideStringsForIsBlank")
  @WithMockUser
  @DatabaseSetup("bookController.xml")
  void shouldReturnBadRequest_whenOneOfIdsAreBlankForRemoveAuthor(String bookId, String categoryId)
      throws Exception {
    mockMvc
        .perform(post("/api/books/{bookId}/authors/{authorId}", bookId, categoryId))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser
  @Transactional
  @DatabaseSetup("bookController.xml")
  void shouldReturnOk_whenRemoveCategoryFromBook() throws Exception {
    mockMvc
        .perform(delete("/api/books/{bookId}/categories/{categoryId}", "book-1", "category-1"))
        .andExpect(status().isOk());

    final var book = bookRepository.findById("book-1").get();
    assertThat(book.getId()).isEqualTo("book-1");
    assertThat(book.getName()).isEqualTo("book-1");
    assertThat(book.getReading()).isEqualTo("reading-1");
    assertThat(book.getSeries()).isEqualTo("series-1");
    assertThat(book.getYear()).isEqualTo(2000);
    assertThat(book.getDuration()).isEqualTo(480);
    assertThat(book.getQuality()).isEqualTo(128);
    assertThat(book.getAuthors().size()).isEqualTo(1);
    assertThat(book.getCategories().size()).isEqualTo(0);

    final var booksCategories = categoryRepository.findById("category-1").get().getBooks();
    assertThat(booksCategories.size()).isEqualTo(0);
  }

  @ParameterizedTest
  @MethodSource("provideStringsForIsBlank")
  @WithMockUser
  @DatabaseSetup("bookController.xml")
  void shouldReturnBadRequest_whenOneOfIdsAreBlankForRemoveCategory(
      String bookId, String categoryId) throws Exception {
    mockMvc
        .perform(post("/api/books/{bookId}/categories/{categoryId}", bookId, categoryId))
        .andExpect(status().isBadRequest());
  }

  private static Stream<Arguments> provideStringsForIsBlank() {
    return Stream.of(Arguments.of("argument-1", "  "), Arguments.of("  ", "argument-2"));
  }
}
