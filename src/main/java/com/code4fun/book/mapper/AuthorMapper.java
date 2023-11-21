package com.code4fun.book.mapper;

import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = EntityToEntityIdsMapper.class)
public interface AuthorMapper {
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "books", ignore = true)
  Author map(AuthorRequestDto requestDto);

  @Mapping(source = "books", target = "bookIds", qualifiedByName = "mapBooksToBookIds")
  AuthorResponseDto map(Author author);

  default Page<AuthorResponseDto> map(Page<Author> page) {
    return page.map(this::map);
  }
}
