package com.code4fun.book.controller;

import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.service.CategoryService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping(path = "/{id}")
  public CategoryResponseDto findById(
      @PathVariable @NotBlank(message = "ID must not be blank.") String id) {
    return categoryService.findById(id);
  }

  @GetMapping
  public Page<CategoryResponseDto> findAll(@PageableDefault Pageable pageable) {
    return categoryService.findAll(pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryResponseDto create(@Valid @RequestBody CategoryRequestDto requestDto) {
    return categoryService.save(requestDto);
  }

  @PutMapping("/{id}")
  public CategoryResponseDto update(
      @PathVariable @NotBlank(message = "ID must not be blank.") String id,
      @Valid @RequestBody CategoryRequestDto requestDto) {
    return categoryService.update(id, requestDto);
  }

  @DeleteMapping(path = "/{id}")
  public void delete(@PathVariable @NotBlank(message = "ID must not be blank.") String id) {
    categoryService.delete(id);
  }
}
