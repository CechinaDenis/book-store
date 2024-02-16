package com.code4fun.book.service;

import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.mapper.CategoryMapper;
import com.code4fun.book.model.Category;
import com.code4fun.book.repository.CategoryRepository;
import javax.persistence.EntityNotFoundException;
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

  public CategoryResponseDto findById(String id) {
    log.info("Getting Category by ID: {}", id);
    final var category = this.getById(id);

    return mapper.map(category);
  }

  public Page<CategoryResponseDto> findAll(Pageable pageable) {
    log.info("Getting all Categories");

    return mapper.map(categoryRepository.findAll(pageable));
  }

  @Transactional
  public CategoryResponseDto save(CategoryRequestDto requestDto) {
    log.info("Saving a new Category");
    final var category = mapper.map(requestDto);

    return mapper.map(categoryRepository.save(category));
  }

  @Transactional
  public CategoryResponseDto update(String id, CategoryRequestDto requestDto) {
    log.info("Updating Category with ID: {}", id);
    final var category = this.getById(id);
    category.setName(requestDto.getName());

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
