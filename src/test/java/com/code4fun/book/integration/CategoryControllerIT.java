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
import com.code4fun.book.repository.CategoryRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

public class CategoryControllerIT extends BaseIntegrationTest {
  @Autowired CategoryRepository categoryRepository;

  @Test
  @WithMockUser
  @DatabaseSetup("categoryController.xml")
  void shouldReturnCategory_whenIdIsValid() throws Exception {
    mockMvc
        .perform(get("/api/categories/{id}", "category-1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("category-1"))
        .andExpect(jsonPath("$.name").value("category-name-1"))
        .andExpect(jsonPath("$.bookIds").isEmpty());
  }

  @Test
  @WithMockUser
  @DatabaseSetup("categoryController.xml")
  void shouldReturnNotFound_whenIdIsInvalid() throws Exception {
    mockMvc
        .perform(get("/api/categories/{id}", "invalid-category"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$").value("Category with id: 'invalid-category' was not found."));
  }

  @Test
  @WithMockUser
  @DatabaseSetup("categoryController.xml")
  void shouldReturnBadRequest_whenIdIsBlank() throws Exception {
    mockMvc
        .perform(get("/api/categories/{id}", "   "))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$").value("findById.id: must not be blank"));
  }

  @Test
  @WithMockUser
  @DatabaseSetup("categoryController.xml")
  void shouldReturnPageOfCategories_whenFindAll() throws Exception {
    mockMvc
        .perform(get("/api/categories"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    FileToStringConverter.getContent(
                        PATH_TO_RESPONSE + "/findAllCategoriesResponse.json")));
  }

  @Test
  @WithMockUser
  @DatabaseSetup("categoryController.xml")
  void shouldReturnCreatedCategory_whenPayloadIsValid() throws Exception {
    mockMvc
        .perform(
            post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"category-name-new\" }"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.name").value("category-name-new"))
        .andExpect(jsonPath("$.bookIds").isEmpty());
  }

  @Test
  @WithMockUser
  @Transactional
  @DatabaseSetup("categoryController.xml")
  void shouldReturnUpdatedCategory_whenPayloadIsValid() throws Exception {
    final var categoryName = "category-name-updated";

    mockMvc
        .perform(
            put("/api/categories/{id}", "category-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{ \"name\": \"%s\" }", categoryName)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("category-1"))
        .andExpect(jsonPath("$.name").value(categoryName));

    final var category = categoryRepository.findById("category-1").get();
    assertThat(category.getName()).isEqualTo(categoryName);
    assertThat(category.getBooks()).isEmpty();
  }

  @Test
  @WithMockUser
  @DatabaseSetup("categoryController.xml")
  void shouldDeleteCategory_whenPayloadIsValid() throws Exception {
    mockMvc.perform(delete("/api/categories/category-1")).andExpect(status().isOk());

    assertThatThrownBy(() -> categoryRepository.findById("category-1").get())
        .isInstanceOf(NoSuchElementException.class);
  }
}
