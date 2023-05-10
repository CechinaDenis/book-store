package com.code4fun.book.dto;

import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.model.Author;
import com.code4fun.book.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
  @Mapping(target = "id", ignore = true)
  Author map(AuthorRequestDto requestDto);
  @Mapping(source = "books", target = "bookIds", qualifiedByName = "mapBooksToBookIds")
  AuthorResponseDto map(Author author);
  List<AuthorResponseDto> map(List<Author> authors);
  @Named("mapBooksToBookIds")
  default Set<Long> mapBooksToBookIds(Set<Book> books) {
    return books.stream()
        .map(Book::getId)
        .collect(Collectors.toSet());
  }
}
