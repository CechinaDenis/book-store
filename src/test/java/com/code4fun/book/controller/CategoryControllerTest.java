package com.code4fun.book.controller;

import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
  @Mock
  CategoryService categoryService;
  @InjectMocks
  CategoryController categoryController;

  @Test
  void findById() {
    final var _id = 1L;
    final var _responseDto = CategoryResponseDto.builder().id(_id).name("Fantasy").build();
    when(categoryService.findById(_id)).thenReturn(_responseDto);

    assertEquals(_responseDto, categoryController.findById(_id));
  }

  @Test
  void findAll() {
    final var _responseDtos = List.of(
        CategoryResponseDto.builder().id(1L).name("Fantasy").build(),
        CategoryResponseDto.builder().id(2L).name("Thriller").build(),
        CategoryResponseDto.builder().id(3L).name("Comedy").build(),
        CategoryResponseDto.builder().id(4L).name("LitRPG").build()
    );
    when(categoryService.findAll()).thenReturn(_responseDtos);

    assertEquals(_responseDtos, categoryController.findAll());
  }

  @Test
  void add() {
    final var _requestDto = CategoryRequestDto.builder().name("Fantasy").build();
    final var _responseDto = CategoryResponseDto.builder().id(1L).name("Fantasy").build();
    when(categoryService.save(_requestDto)).thenReturn(_responseDto);

    assertEquals(new ResponseEntity<>(_responseDto, HttpStatus.CREATED), categoryController.add(_requestDto));
  }

  @Test
  void edit() {
    final var _requestDto = CategoryRequestDto.builder().id(1L).name("Fantasy").build();
    final var _responseDto = CategoryResponseDto.builder().id(1L).name("Fantasy").build();
    when(categoryService.update(isA(CategoryRequestDto.class))).thenReturn(_responseDto);

    assertEquals(_responseDto, categoryController.edit(_requestDto));
  }

  @Test
  void delete() {
    doNothing().when(categoryService).delete(isA(Long.class));

    assertEquals(new ResponseEntity<>(HttpStatus.OK), categoryController.delete(1L));
  }
}