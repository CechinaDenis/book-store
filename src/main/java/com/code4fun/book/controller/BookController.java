package com.code4fun.book.controller;

import com.code4fun.book.dto.requestDto.BookRequestDto;
import com.code4fun.book.dto.responseDto.BookResponseDto;
import com.code4fun.book.service.BookService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/books")
public class BookController {
  private final BookService bookService;

  @GetMapping("/{id}")
  public BookResponseDto findById(@PathVariable @NotBlank String id) {
    return bookService.findById(id);
  }

  @GetMapping
  public Page<BookResponseDto> findAll(@PageableDefault Pageable pageable) {
    return bookService.findAll(pageable);
  }

  @PostMapping
  public BookResponseDto add(@Valid @RequestBody BookRequestDto requestDto) {
    return bookService.save(requestDto);
  }

  @PostMapping("/{bookId}/authors/{authorId}")
  public BookResponseDto addAuthorToBook(
      @PathVariable @NotBlank String bookId, @PathVariable @NotBlank String authorId) {
    return bookService.addAuthor(bookId, authorId);
  }

  @PostMapping("/{bookId}/categories/{categoryId}")
  public BookResponseDto addCategoryToBook(
      @PathVariable @NotBlank String bookId, @PathVariable @NotBlank String categoryId) {
    return bookService.addCategory(bookId, categoryId);
  }

  @PutMapping
  public BookResponseDto edit(@Valid @RequestBody BookRequestDto requestDto) {
    return bookService.update(requestDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable @NotBlank String id) {
    bookService.delete(id);
  }

  @DeleteMapping("/{id}/authors/{authorId}")
  public void removeAuthorFromBook(
      @PathVariable @NotBlank String id, @PathVariable @NotBlank String authorId) {
    bookService.removeAuthor(id, authorId);
  }

  @DeleteMapping("/{id}/categories/{categoryId}")
  public void removeCategoryFromBook(
      @PathVariable @NotBlank String id, @PathVariable @NotBlank String categoryId) {
    bookService.removeCategory(id, categoryId);
  }
}
