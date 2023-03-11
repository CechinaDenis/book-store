package com.code4fun.book.service;

import com.code4fun.book.dto.mapper;
import com.code4fun.book.dto.requestDto.BookRequestDto;
import com.code4fun.book.dto.responseDto.BookResponseDto;
import com.code4fun.book.exception.ErrorDetails;
import com.code4fun.book.exception.ResourceNotFoundException;
import com.code4fun.book.model.Book;
import com.code4fun.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService implements MyService<BookRequestDto, BookResponseDto, Long> {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final Clock clock;

    @Override
    public BookResponseDto findById(Long id) {
        log.info("Getting Book by ID: {}", id);
        final var _book = this.getById(id);
        return mapper.bookToBookResponseDto(_book);
    }

    @Override
    public List<BookResponseDto> findAll() {
        log.info("Getting all Books");
        return mapper.booksToBookResponseDtos(bookRepository.findAll());
    }

    @Override
    public BookResponseDto save(BookRequestDto requestDto) {
        log.info("Saving a new Book: {}", requestDto);
        final var _book = mapper.bookRequestDtoToBook(requestDto);
        return mapper.bookToBookResponseDto(bookRepository.save(_book));
    }

    @Transactional
    @Override
    public BookResponseDto update(BookRequestDto requestDto) {
        final var id = requestDto.getId();
        log.info("Updating Book with ID: {}", id);
        final var _book = this.getById(id);
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
        log.info("Deleting Book with ID: {}", id);
        final var _book = this.getById(id);
        bookRepository.delete(_book);
    }

    @Transactional
    public BookResponseDto addAuthor(Long bookId, Long authorId) {
        log.info("Adding Author with ID: {} to the Book with ID: {}", authorId, bookId);
        final var _book = this.getById(bookId);
        final var _author = authorService.getById(authorId);
        _book.addAuthor(_author);
        return mapper.bookToBookResponseDto(_book);
    }

    @Transactional
    public void removeAuthor(Long bookId, Long authorId) {
        log.info("Removing Author with ID: {} from the Book with ID: {}", authorId, bookId);
        final var _book = this.getById(bookId);
        final var _author = authorService.getById(authorId);
        _book.removeAuthor(_author);
    }

    @Transactional
    public BookResponseDto addCategory(Long bookId, Long categoryId) {
        log.info("Adding Category with ID: {} to the Book with ID: {}", categoryId, bookId);
        final var _book = this.getById(bookId);
        final var _category = categoryService.getById(categoryId);
        _book.addCategory(_category);
        return mapper.bookToBookResponseDto(_book);
    }

    @Transactional
    public void removeCategory(Long bookId, Long categoryId) {
        log.info("Removing Category with ID: {} from the Book with ID: {}", categoryId, bookId);
        final var _book = this.getById(bookId);
        final var _category = categoryService.getById(categoryId);
        _book.removeCategory(_category);
    }

    Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    final ErrorDetails details = new ErrorDetails(LocalDateTime.now(clock), "Book Id", "Id", id);
                    return new ResourceNotFoundException(details);
                });
    }
}