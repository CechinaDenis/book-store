package com.code4fun.book.mapper;

import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = EntityToEntityIdsMapper.class)
public interface CategoryMapper {
  @Mapping(target = "books", ignore = true)
  Category map(CategoryRequestDto requestDto);

  default Page<CategoryResponseDto> map(Page<Category> page) {
    return page.map(this::map);
  }

  @Mapping(source = "books", target = "bookIds", qualifiedByName = "mapBooksToBookIds")
  CategoryResponseDto map(Category category);
}
