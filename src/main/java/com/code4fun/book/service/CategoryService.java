package com.code4fun.book.service;

import com.code4fun.book.dto.mapper;
import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.exception.ErrorDetails;
import com.code4fun.book.exception.ResourceNotFoundException;
import com.code4fun.book.model.Category;
import com.code4fun.book.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService implements MyService<CategoryRequestDto, CategoryResponseDto, Long> {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDto findById(Long id) {
        final var _category = this.getById(id);
        return mapper.categoryToCategoryResponseDto(_category);
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        return mapper.categoriesToCategoryResponseDtos(categoryRepository.findAll());
    }

    @Override
    public CategoryResponseDto save(CategoryRequestDto requestDto) {
        final var _category = mapper.categoryRequestDtoToCategory(requestDto);
        return mapper.categoryToCategoryResponseDto(categoryRepository.save(_category));
    }

    @Transactional
    @Override
    public CategoryResponseDto update(CategoryRequestDto requestDto) {
        final var _category = this.getById(requestDto.getId());
        _category.setName(requestDto.getName());
        return mapper.categoryToCategoryResponseDto(_category);
    }

    @Override
    public void delete(Long id) {
        final var _category = getById(id);
        categoryRepository.delete(_category);
    }

    Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            final ErrorDetails details = new ErrorDetails(new Date(), "Category Id", "Id", id);
            return new ResourceNotFoundException(details);
        });
    }
}
