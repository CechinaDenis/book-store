package com.code4fun.book.controller;

import com.code4fun.book.dto.requestDto.BookRequestDto;
import com.code4fun.book.dto.responseDto.BookResponseDto;
import com.code4fun.book.service.BookService;
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
@RequestMapping(path = "/api/books", produces = "application/json; charset=utf-8")
public class BookController {
    private final BookService bookService;

    @GetMapping(path = "/{id}")
    public BookResponseDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    public List<BookResponseDto> findAll() {
        return bookService.findAll();
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> add(@RequestBody BookRequestDto requestDto) {
        final var _responseDto = bookService.save(requestDto);
        return new ResponseEntity<>(_responseDto, HttpStatus.CREATED);
    }

    @PostMapping(path = "/{bookId}/authors/{authorId}")
    public ResponseEntity<BookResponseDto> addAuthorToBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId
    ) {
        final var responseDto = bookService.addAuthor(bookId, authorId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping(path = "/{bookId}/categories/{categoryId}")
    public ResponseEntity<BookResponseDto> addCategoryToBook(
            @PathVariable Long bookId,
            @PathVariable Long categoryId
    ) {
        final var responseDto = bookService.addCategory(bookId, categoryId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping
    public BookResponseDto edit(@RequestBody BookRequestDto requestDto) {
        return bookService.update(requestDto);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @DeleteMapping(path = "/{bookId}/authors/{authorId}")
    public void removeAuthorFromBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId
    ) {
        bookService.removeAuthor(bookId, authorId);
    }

    @DeleteMapping(path = "/{bookId}/categories/{categoryId}")
    public void removeCategoryFromBook(
            @PathVariable Long bookId,
            @PathVariable Long categoryId
    ) {
        bookService.removeCategory(bookId, categoryId);
    }
}