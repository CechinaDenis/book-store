package com.code4fun.book.mapper;

import com.code4fun.book.dto.request.CategoryRequest;
import com.code4fun.book.dto.response.CategoryResponse;
import com.code4fun.book.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = EntityToEntityIdsMapper.class)
public interface CategoryMapper {
  @Mapping(target = "books", ignore = true)
  Category map(CategoryRequest request);

  default Page<CategoryResponse> map(Page<Category> page) {
    return page.map(this::map);
  }

  @Mapping(source = "books", target = "bookIds", qualifiedByName = "mapBooksToBookIds")
  CategoryResponse map(Category category);
}
