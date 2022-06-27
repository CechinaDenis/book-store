package com.code4fun.book.service;

import com.code4fun.book.dto.mapper;
import com.code4fun.book.dto.requestDto.BookRequestDto;
import com.code4fun.book.dto.responseDto.BookResponseDto;
import com.code4fun.book.exception.ErrorDetails;
import com.code4fun.book.exception.ResourceNotFoundException;
import com.code4fun.book.model.Book;
import com.code4fun.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements MyService<BookRequestDto, BookResponseDto, Long> {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    private Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    final ErrorDetails details = new ErrorDetails(new Date(), "Book Id", "Id", id);
                    return new ResourceNotFoundException(details);
                });
    }

    @Override
    public BookResponseDto findById(Long id) {
        final var _book = this.getById(id);
        return mapper.bookToBookResponseDto(_book);
    }

    @Override
    public List<BookResponseDto> findAll() {
        return mapper.booksToBookResponseDtos(bookRepository.findAll());
    }

    @Override
    public BookResponseDto save(BookRequestDto requestDto) {
        final var _book = mapper.bookRequestDtoToBook(requestDto);
        return mapper.bookToBookResponseDto(bookRepository.save(_book));
    }

    @Transactional
    @Override
    public BookResponseDto update(BookRequestDto requestDto) {
        final var _book = this.getById(requestDto.getId());
        _book.setName(requestDto.getName());
        _book.setReading(requestDto.getReading());
        _book.setSeries(requestDto.getSeries());
        _book.setYear(requestDto.getYear());
        _book.setDuration(requestDto.getDuration());
        _book.setQuality(requestDto.getQuality());
        return mapper.bookToBookResponseDto(_book);
    }

    @Override
    public void delete(Long id) {
        final var _book = this.getById(id);
        bookRepository.delete(_book);
    }

    @Transactional
    public BookResponseDto addAuthor(Long bookId, Long authorId) {
        final var _book = this.getById(bookId);
        final var _author = authorService.getById(authorId);
        _book.addAuthor(_author);
        return mapper.bookToBookResponseDto(_book);
    }

    @Transactional
    public void removeAuthor(Long bookId, Long authorId) {
        final var _book = this.getById(bookId);
        final var _author = authorService.getById(authorId);
        _book.removeAuthor(_author);
    }

    @Transactional
    public BookResponseDto addCategory(Long bookId, Long categoryId) {
        final var _book = this.getById(bookId);
        final var _category = categoryService.getById(categoryId);
        _book.addCategory(_category);
        return mapper.bookToBookResponseDto(_book);
    }

    @Transactional
    public void removeCategory(Long bookId, Long categoryId) {
        final var _book = this.getById(bookId);
        final var _category = categoryService.getById(categoryId);
        _book.removeCategory(_category);
    }
}