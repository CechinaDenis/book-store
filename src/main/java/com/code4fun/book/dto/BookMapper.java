package com.code4fun.book.dto;

import com.code4fun.book.dto.requestDto.BookRequestDto;
import com.code4fun.book.dto.responseDto.BookResponseDto;
import com.code4fun.book.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = EntityToEntityIdsMapper.class)
public interface BookMapper {
  @Mapping(target = "authors", ignore = true)
  @Mapping(target = "categories", ignore = true)
  Book map(BookRequestDto requestDto);

  List<BookResponseDto> map(List<Book> books);

  @Mapping(source = "authors", target = "authorIds", qualifiedByName = "mapAuthorsToAuthorIds")
  @Mapping(source = "categories", target = "categoryIds", qualifiedByName = "mapCategoriesToCategoryIds")
  BookResponseDto map(Book book);
}
