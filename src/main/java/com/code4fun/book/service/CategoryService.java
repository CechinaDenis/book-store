package com.code4fun.book.service;

import com.code4fun.book.dto.request.CategoryRequest;
import com.code4fun.book.dto.response.CategoryResponse;
import com.code4fun.book.mapper.CategoryMapper;
import com.code4fun.book.model.Category;
import com.code4fun.book.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper mapper;

  public CategoryResponse findById(String id) {
    log.info("Getting Category by ID: {}", id);
    final var category = this.getById(id);

    return mapper.map(category);
  }

  public Page<CategoryResponse> findAll(Pageable pageable) {
    log.info("Getting all Categories");

    return mapper.map(categoryRepository.findAll(pageable));
  }

  @Transactional
  public CategoryResponse save(CategoryRequest request) {
    log.info("Saving a new Category");
    final var category = mapper.map(request);

    return mapper.map(categoryRepository.save(category));
  }

  @Transactional
  public CategoryResponse update(String id, CategoryRequest request) {
    log.info("Updating Category with ID: {}", id);
    final var category = this.getById(id);
    category.setName(request.name());

    return mapper.map(category);
  }

  @Transactional
  public void delete(String id) {
    log.info("Deleting Category by ID: {}", id);
    final var category = getById(id);
    categoryRepository.delete(category);
  }

  Category getById(String id) {
    return categoryRepository
        .findById(id)
        .orElseThrow(
            () -> {
              var message = String.format("Category with id: '%s' was not found.", id);
              log.warn(message);

              return new EntityNotFoundException(message);
            });
  }
}
