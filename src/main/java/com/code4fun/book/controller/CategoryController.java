package com.code4fun.book.controller;

import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/categories", produces = "application/json; charset=utf-8")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(path = "/{id}")
    public CategoryResponseDto findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping
    public List<CategoryResponseDto> findAll() {
        return categoryService.findAll();
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> add(@RequestBody CategoryRequestDto requestDto) {
        final var _responseDto = categoryService.save(requestDto);
        return new ResponseEntity<>(_responseDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CategoryResponseDto> edit(@RequestBody CategoryRequestDto requestDto) {
        final var _responseDto = categoryService.update(requestDto);
        return new ResponseEntity<>(_responseDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
