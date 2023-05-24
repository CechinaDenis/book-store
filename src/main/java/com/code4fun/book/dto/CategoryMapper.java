package com.code4fun.book.dto;

import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = EntityToEntityIdsMapper.class)
public interface CategoryMapper {
  @Mapping(target = "books", ignore = true)
  Category map(CategoryRequestDto requestDto);

  List<CategoryResponseDto> map(List<Category> categories);

  @Mapping(source = "books", target = "bookIds", qualifiedByName = "mapBooksToBookIds")
  CategoryResponseDto map(Category category);
}
