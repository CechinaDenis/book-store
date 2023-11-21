package com.code4fun.book.mapper;

import com.code4fun.book.dto.requestDto.BookRequestDto;
import com.code4fun.book.dto.responseDto.BookResponseDto;
import com.code4fun.book.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = EntityToEntityIdsMapper.class)
public interface BookMapper {
  @Mapping(target = "authors", ignore = true)
  @Mapping(target = "categories", ignore = true)
  Book map(BookRequestDto requestDto);

  default Page<BookResponseDto> map(Page<Book> page) {
    return page.map(this::map);
  }

  @Mapping(source = "authors", target = "authorIds", qualifiedByName = "mapAuthorsToAuthorIds")
  @Mapping(source = "categories", target = "categoryIds", qualifiedByName = "mapCategoriesToCategoryIds")
  BookResponseDto map(Book book);
}
