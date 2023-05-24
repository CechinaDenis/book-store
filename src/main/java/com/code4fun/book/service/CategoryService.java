package com.code4fun.book.service;

import com.code4fun.book.dto.CategoryMapper;
import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.exception.ErrorDetails;
import com.code4fun.book.exception.ResourceNotFoundException;
import com.code4fun.book.model.Category;
import com.code4fun.book.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService implements MyService<CategoryRequestDto, CategoryResponseDto, Long> {
  private final CategoryRepository categoryRepository;
  private final Clock clock;
  private final CategoryMapper mapper;

  @Override
  public CategoryResponseDto findById(Long id) {
    log.info("Getting Category by ID: {}", id);
    final var _category = this.getById(id);
    return mapper.map(_category);
  }

  @Override
  public List<CategoryResponseDto> findAll() {
    log.info("Getting all Categories");
    return mapper.map(categoryRepository.findAll());
  }

  @Override
  public CategoryResponseDto save(CategoryRequestDto requestDto) {
    log.info("Saving a new Category");
    final var _category = mapper.map(requestDto);
    return mapper.map(categoryRepository.save(_category));
  }

  @Transactional
  @Override
  public CategoryResponseDto update(CategoryRequestDto requestDto) {
    final var id = requestDto.getId();
    log.info("Updating Category with ID: {}", id);
    final var _category = this.getById(id);
    _category.setName(requestDto.getName());
    return mapper.map(_category);
  }

  @Override
  public void delete(Long id) {
    log.info("Deleting Category by ID: {}", id);
    final var _category = getById(id);
    categoryRepository.delete(_category);
  }

  Category getById(Long id) {
    return categoryRepository.findById(id).orElseThrow(() -> {
      final var details = new ErrorDetails(LocalDateTime.now(clock), "Category Id", "Id", id);
      return new ResourceNotFoundException(details);
    });
  }
}
