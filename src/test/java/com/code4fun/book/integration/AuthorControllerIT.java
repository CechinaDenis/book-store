package com.code4fun.book.integration;

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
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

public class AuthorControllerIT extends BaseIntegrationTest {
  @Autowired AuthorRepository authorRepository;

  @Test
  @WithMockUser
  @DatabaseSetup("authorController.xml")
  void shouldReturnAuthor_whenIdIsValid() throws Exception {
    mockMvc
        .perform(get("/api/authors/{id}", "author-1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("author-1"))
        .andExpect(jsonPath("$.firstName").value("first-name-author-1"))
        .andExpect(jsonPath("$.lastName").value("last-name-author-1"))
        .andExpect(jsonPath("$.bookIds").isEmpty());
  }

  @Test
  @WithMockUser
  @DatabaseSetup("authorController.xml")
  void shouldReturnNotFound_whenIdIsInvalid() throws Exception {
    mockMvc
        .perform(get("/api/authors/{id}", "invalid-author"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$").value("Author with id: 'invalid-author' was not found."));
  }

  @Test
  @WithMockUser
  @DatabaseSetup("authorController.xml")
  void shouldReturnBadRequest_whenIdIsBlank() throws Exception {
    mockMvc
        .perform(get("/api/authors/{id}", "   "))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$").value("findById.id: ID must not be blank."));
  }

  @Test
  @WithMockUser
  @DatabaseSetup("authorController.xml")
  void shouldReturnPageOfAuthors_whenFindAll() throws Exception {
    mockMvc
        .perform(get("/api/authors"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    FileToStringConverter.getContent(
                        PATH_TO_RESPONSE + "/findAllAuthorsResponse.json")));
  }

  @Test
  @WithMockUser
  @DatabaseSetup("authorController.xml")
  void shouldReturnCreatedAuthor_whenPayloadIsValid() throws Exception {
    mockMvc
        .perform(
            post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                          "firstName": "first-name-author-1",
                          "lastName": "last-name-author-1"
                        }
                        """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.firstName").value("first-name-author-1"))
        .andExpect(jsonPath("$.lastName").value("last-name-author-1"));
  }

  @Test
  @WithMockUser
  @Transactional
  @DatabaseSetup("authorController.xml")
  void shouldReturnUpdatedAuthor_whenPayloadIsValid() throws Exception {
    final var firstName = "first-name-author-new";
    final var lastName = "last-name-author-new";

    mockMvc
        .perform(
            put("/api/authors/author-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    String.format(
                        """
                            {
                              "firstName": "%s",
                              "lastName": "%s"
                            }
                            """,
                        firstName, lastName)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("author-1"))
        .andExpect(jsonPath("$.firstName").value(firstName))
        .andExpect(jsonPath("$.lastName").value(lastName));

    final var author = authorRepository.findById("author-1").get();

    assertThat(author.getFirstName()).isEqualTo(firstName);
    assertThat(author.getLastName()).isEqualTo(lastName);
    assertThat(author.getBooks()).isEmpty();
  }

  @Test
  @WithMockUser
  @DatabaseSetup("authorController.xml")
  void shouldDeleteAuthor_whenPayloadIsValid() throws Exception {
    mockMvc.perform(delete("/api/authors/author-1")).andExpect(status().isOk());

    assertThatThrownBy(() -> authorRepository.findById("author-1").get())
        .isInstanceOf(NoSuchElementException.class);
  }
}
