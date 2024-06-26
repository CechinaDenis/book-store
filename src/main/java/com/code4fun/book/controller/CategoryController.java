package com.code4fun.book.controller;

import com.code4fun.book.dto.request.CategoryRequest;
import com.code4fun.book.dto.response.CategoryResponse;
import com.code4fun.book.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping(path = "/{id}")
  public CategoryResponse findById(@PathVariable String id) {
    return categoryService.findById(id);
  }

  @GetMapping
  public Page<CategoryResponse> findAll(@PageableDefault Pageable pageable) {
    return categoryService.findAll(pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryResponse create(@RequestBody CategoryRequest request) {
    return categoryService.save(request);
  }

  @PutMapping("/{id}")
  public CategoryResponse update(@PathVariable String id, @RequestBody CategoryRequest request) {
    return categoryService.update(id, request);
  }

  @DeleteMapping(path = "/{id}")
  public void delete(@PathVariable String id) {
    categoryService.delete(id);
  }
}
