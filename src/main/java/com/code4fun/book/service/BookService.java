package com.code4fun.book.service;

import com.code4fun.book.dto.requestDto.BookRequestDto;
import com.code4fun.book.dto.responseDto.BookResponseDto;
import com.code4fun.book.exception.ErrorDetails;
import com.code4fun.book.exception.ResourceNotFoundException;
import com.code4fun.book.mapper.BookMapper;
import com.code4fun.book.model.Book;
import com.code4fun.book.repository.BookRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorService authorService;
  private final CategoryService categoryService;
  private final Clock clock;
  private final BookMapper mapper;

  public BookResponseDto findById(String id) {
    log.info("Getting Book by ID: {}", id);
    final var book = this.getById(id);
    return mapper.map(book);
  }

  public Page<BookResponseDto> findAll(Pageable pageable) {
    log.info("Getting all Books");
    return mapper.map(bookRepository.findAll(pageable));
  }

  public BookResponseDto save(BookRequestDto requestDto) {
    log.info("Saving a new Book: {}", requestDto);
    final var book = mapper.map(requestDto);
    return mapper.map(bookRepository.save(book));
  }

  @Transactional
  public BookResponseDto update(BookRequestDto requestDto) {
    final var id = requestDto.getUuid();
    log.info("Updating Book with ID: {}", id);
    final var book = this.getById(id);
    book.setName(requestDto.getName());
    book.setReading(requestDto.getReading());
    book.setSeries(requestDto.getSeries());
    book.setYear(requestDto.getYear());
    book.setDuration(requestDto.getDuration());
    book.setQuality(requestDto.getQuality());
    return mapper.map(book);
  }

  public void delete(String id) {
    log.info("Deleting Book with ID: {}", id);
    final var book = this.getById(id);
    bookRepository.delete(book);
  }

  @Transactional
  public BookResponseDto addAuthor(String bookId, String authorId) {
    log.info("Adding Author with ID: {} to the Book with ID: {}", authorId, bookId);
    final var book = this.getById(bookId);
    final var author = authorService.getById(authorId);
    book.addAuthor(author);
    return mapper.map(book);
  }

  @Transactional
  public void removeAuthor(String bookId, String authorId) {
    log.info("Removing Author with ID: {} from the Book with ID: {}", authorId, bookId);
    final var book = this.getById(bookId);
    final var author = authorService.getById(authorId);
    book.removeAuthor(author);
  }

  @Transactional
  public BookResponseDto addCategory(String bookId, String categoryId) {
    log.info("Adding Category with ID: {} to the Book with ID: {}", categoryId, bookId);
    final var book = this.getById(bookId);
    final var _category = categoryService.getById(categoryId);
    book.addCategory(_category);
    return mapper.map(book);
  }

  @Transactional
  public void removeCategory(String bookId, String categoryId) {
    log.info("Removing Category with ID: {} from the Book with ID: {}", categoryId, bookId);
    final var book = this.getById(bookId);
    final var _category = categoryService.getById(categoryId);
    book.removeCategory(_category);
  }

  Book getById(String id) {
    return bookRepository
        .findById(id)
        .orElseThrow(
            () -> {
              final ErrorDetails details =
                  new ErrorDetails(LocalDateTime.now(clock), "Book Id", "Id", id);
              return new ResourceNotFoundException(details);
            });
  }
}
