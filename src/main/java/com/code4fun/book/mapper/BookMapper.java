package com.code4fun.book.mapper;

import com.code4fun.book.dto.request.BookRequest;
import com.code4fun.book.dto.response.BookResponse;
import com.code4fun.book.model.Book;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(
    componentModel = "spring",
    uses = EntityToEntityIdsMapper.class,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookMapper {
  @Mapping(target = "authors", ignore = true)
  @Mapping(target = "categories", ignore = true)
  Book map(BookRequest request);

  default Page<BookResponse> map(Page<Book> page) {
    return page.map(this::map);
  }

  @Mapping(source = "authors", target = "authorIds", qualifiedByName = "mapAuthorsToAuthorIds")
  @Mapping(
      source = "categories",
      target = "categoryIds",
      qualifiedByName = "mapCategoriesToCategoryIds")
  BookResponse map(Book book);
}
