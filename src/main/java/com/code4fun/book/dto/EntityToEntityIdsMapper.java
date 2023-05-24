package com.code4fun.book.dto;

import com.code4fun.book.model.Author;
import com.code4fun.book.model.Book;
import com.code4fun.book.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EntityToEntityIdsMapper {
  @Named("mapAuthorsToAuthorIds")
  default Set<Long> mapAuthorsToAuthorIds(Set<Author> authors) {
    return authors.stream()
        .map(Author::getId)
        .collect(Collectors.toSet());
  }

  @Named("mapBooksToBookIds")
  default Set<Long> mapBooksToBookIds(Set<Book> books) {
    return books.stream()
        .map(Book::getId)
        .collect(Collectors.toSet());
  }

  @Named("mapCategoriesToCategoryIds")
  default Set<Long> mapCategoriesToCategoryIds(Set<Category> categories) {
    return categories.stream()
        .map(Category::getId)
        .collect(Collectors.toSet());
  }
}
