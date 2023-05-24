package com.code4fun.book.dto;

import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = EntityToEntityIdsMapper.class)
public interface AuthorMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "books", ignore = true)
  Author map(AuthorRequestDto requestDto);

  @Mapping(source = "books", target = "bookIds", qualifiedByName = "mapBooksToBookIds")
  AuthorResponseDto map(Author author);

  List<AuthorResponseDto> map(List<Author> authors);
}
