package com.code4fun.book.controllers;

import com.code4fun.book.model.Book;
import com.code4fun.book.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping(path = "/{id}", produces = "application/json; charset=utf-8")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        final Book book = bookService.findById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        List<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> add(@RequestBody Book newBook) {
        final Book book = bookService.save(newBook);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> edit(@PathVariable Long id, @RequestBody Book editedBook) {
        Book book = bookService.update(editedBook, id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
