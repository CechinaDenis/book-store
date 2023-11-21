package com.code4fun.book.controller;

import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/categories", produces = "application/json; charset=utf-8")
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping(path = "/{id}")
  public CategoryResponseDto findById(@PathVariable String id) {
    return categoryService.findById(id);
  }

  @GetMapping
  public Page<CategoryResponseDto> findAll(@PageableDefault Pageable pageable) {
    return categoryService.findAll(pageable);
  }

  @PostMapping
  public CategoryResponseDto add(@RequestBody CategoryRequestDto requestDto) {
    return categoryService.save(requestDto);
  }

  @PutMapping
  public CategoryResponseDto edit(@RequestBody CategoryRequestDto requestDto) {
    return categoryService.update(requestDto);
  }

  @DeleteMapping(path = "/{id}")
  public void delete(@PathVariable String id) {
    categoryService.delete(id);
  }
}