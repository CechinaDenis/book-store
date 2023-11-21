package com.code4fun.book.service;

import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.exception.ErrorDetails;
import com.code4fun.book.exception.ResourceNotFoundException;
import com.code4fun.book.mapper.CategoryMapper;
import com.code4fun.book.model.Category;
import com.code4fun.book.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final Clock clock;
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

  public CategoryResponseDto save(CategoryRequestDto requestDto) {
    log.info("Saving a new Category");
    final var category = mapper.map(requestDto);
    return mapper.map(categoryRepository.save(category));
  }

  @Transactional
  public CategoryResponseDto update(CategoryRequestDto requestDto) {
    final var id = requestDto.getUuid();
    log.info("Updating Category with ID: {}", id);
    final var category = this.getById(id);
    category.setName(requestDto.getName());
    return mapper.map(category);
  }

  public void delete(String id) {
    log.info("Deleting Category by ID: {}", id);
    final var category = getById(id);
    categoryRepository.delete(category);
  }

  Category getById(String id) {
    return categoryRepository.findById(id).orElseThrow(() -> {
      final var details = new ErrorDetails(LocalDateTime.now(clock), "Category Id", "Id", id);
      return new ResourceNotFoundException(details);
    });
  }
}
