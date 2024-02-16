package com.code4fun.book.mapper;

import com.code4fun.book.model.Author;
import com.code4fun.book.model.Book;
import com.code4fun.book.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EntityToEntityIdsMapper {
  @Named("mapAuthorsToAuthorIds")
  default Set<String> mapAuthorsToAuthorIds(Set<Author> authors) {
    return authors.stream().map(Author::getId).collect(Collectors.toSet());
  }

  @Named("mapBooksToBookIds")
  default Set<String> mapBooksToBookIds(Set<Book> books) {
    return books.stream().map(Book::getId).collect(Collectors.toSet());
  }

  @Named("mapCategoriesToCategoryIds")
  default Set<String> mapCategoriesToCategoryIds(Set<Category> categories) {
    return categories.stream().map(Category::getId).collect(Collectors.toSet());
  }
}
