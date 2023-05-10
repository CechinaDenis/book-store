package com.code4fun.book.dto;

import com.code4fun.book.dto.requestDto.BookRequestDto;
import com.code4fun.book.dto.responseDto.BookResponseDto;
import com.code4fun.book.model.Author;
import com.code4fun.book.model.Book;
import com.code4fun.book.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {
  Book map(BookRequestDto requestDto);

  List<BookResponseDto> map(List<Book> books);

  @Mapping(source = "authors", target = "authorIds", qualifiedByName = "mapAuthorsToAuthorIds")
  @Mapping(source = "categories", target = "categoryIds", qualifiedByName = "mapCategoriesToCategoryIds")
  BookResponseDto map(Book book);

  @Named("mapAuthorsToAuthorIds")
  default Set<Long> mapAuthorsToAuthorIds(Set<Author> authors) {
    return authors.stream()
        .map(Author::getId)
        .collect(Collectors.toSet());
  }

  @Named("mapCategoriesToCategoryIds")
  default Set<Long> mapCategoriesToCategoryIds(Set<Category> categories) {
    return categories.stream()
        .map(Category::getId)
        .collect(Collectors.toSet());
  }
}
