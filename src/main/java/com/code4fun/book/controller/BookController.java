package com.code4fun.book.controller;

import com.code4fun.book.dto.request.BookRequest;
import com.code4fun.book.dto.response.BookResponse;
import com.code4fun.book.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping(path = "/api/books")
public class BookController {
  private final BookService bookService;

  @GetMapping("/{id}")
  public BookResponse findById(
      @PathVariable @NotBlank(message = "ID must not be blank.") String id) {
    return bookService.findById(id);
  }

  @GetMapping
  public Page<BookResponse> findAll(@PageableDefault Pageable pageable) {
    return bookService.findAll(pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookResponse create(@Valid @RequestBody BookRequest request) {
    return bookService.save(request);
  }

  @PutMapping("/{id}")
  public BookResponse update(
      @PathVariable @NotBlank String id, @Valid @RequestBody BookRequest request) {
    return bookService.update(id, request);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable @NotBlank String id) {
    bookService.delete(id);
  }

  @PostMapping("/{bookId}/authors/{authorId}")
  public BookResponse addAuthorToBook(
      @PathVariable @NotBlank String bookId, @PathVariable @NotBlank String authorId) {
    return bookService.addAuthor(bookId, authorId);
  }

  @PostMapping("/{bookId}/categories/{categoryId}")
  public BookResponse addCategoryToBook(
      @PathVariable @NotBlank String bookId, @PathVariable @NotBlank String categoryId) {
    return bookService.addCategory(bookId, categoryId);
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
