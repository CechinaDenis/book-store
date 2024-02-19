package com.code4fun.book.mapper;

import com.code4fun.book.dto.request.AuthorRequest;
import com.code4fun.book.dto.response.AuthorResponse;
import com.code4fun.book.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = EntityToEntityIdsMapper.class)
public interface AuthorMapper {
  @Mapping(target = "books", ignore = true)
  Author map(AuthorRequest requestDto);

  @Mapping(source = "books", target = "bookIds", qualifiedByName = "mapBooksToBookIds")
  AuthorResponse map(Author author);

  default Page<AuthorResponse> map(Page<Author> page) {
    return page.map(this::map);
  }
}
