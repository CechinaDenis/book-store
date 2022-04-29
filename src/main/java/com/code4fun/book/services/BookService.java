package com.code4fun.book.services;

import com.code4fun.book.exception.ErrorDetails;
import com.code4fun.book.exception.ResourceNotFoundException;
import com.code4fun.book.model.Book;
import com.code4fun.book.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    final ErrorDetails details = new ErrorDetails(new Date(), "Book Id", "Id", id);
                    return new ResourceNotFoundException(details);
                });
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book, Long id) {
        Book _book = this.findById(id);
        _book.setDuration(book.getDuration());
        _book.setQuality(book.getQuality());
        _book.setSeries(book.getSeries());
        _book.setReading(book.getReading());
        _book.setYear(book.getYear());
        return this.save(_book);
    }
}
