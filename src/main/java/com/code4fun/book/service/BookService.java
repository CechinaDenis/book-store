package com.code4fun.book.service;

import com.code4fun.book.dto.request.BookRequest;
import com.code4fun.book.dto.response.BookResponse;
import com.code4fun.book.mapper.BookMapper;
import com.code4fun.book.model.Book;
import com.code4fun.book.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
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
  private final BookMapper mapper;

  public BookResponse findById(String id) {
    log.info("Getting Book by ID: {}", id);
    final var book = this.getById(id);

    return mapper.map(book);
  }

  public Page<BookResponse> findAll(Pageable pageable) {
    log.info("Getting all Books");

    return mapper.map(bookRepository.findAll(pageable));
  }

  public BookResponse save(BookRequest request) {
    log.info("Saving a new Book: {}", request);
    final var book = mapper.map(request);

    return mapper.map(bookRepository.save(book));
  }

  @Transactional
  public BookResponse update(String id, BookRequest request) {
    log.info("Updating Book with ID: {}", id);
    final var book = this.getById(id);
    book.setName(request.name());
    book.setReading(request.reading());
    book.setSeries(request.series());
    book.setYear(request.year());
    book.setDuration(request.duration());
    book.setQuality(request.quality());

    return mapper.map(book);
  }

  public void delete(String id) {
    log.info("Deleting Book with ID: {}", id);
    final var book = this.getById(id);
    book.getAuthors().forEach(a -> a.removeBook(book));
    book.getCategories().forEach(c -> c.removeBook(book));
    bookRepository.delete(book);
  }

  @Transactional
  public BookResponse addAuthor(String bookId, String authorId) {
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
    author.removeBook(book);
    book.removeAuthor(author);
  }

  @Transactional
  public BookResponse addCategory(String bookId, String categoryId) {
    log.info("Adding Category with ID: {} to the Book with ID: {}", categoryId, bookId);
    final var book = this.getById(bookId);
    final var category = categoryService.getById(categoryId);
    book.addCategory(category);

    return mapper.map(book);
  }

  @Transactional
  public void removeCategory(String bookId, String categoryId) {
    log.info("Removing Category with ID: {} from the Book with ID: {}", categoryId, bookId);
    final var book = this.getById(bookId);
    final var category = categoryService.getById(categoryId);
    category.removeBook(book);
    book.removeCategory(category);
  }

  Book getById(String id) {
    return bookRepository
        .findById(id)
        .orElseThrow(
            () -> {
              final var message = String.format("Book with id: '%s' was not found.", id);
              log.warn(message);

              return new EntityNotFoundException(message);
            });
  }
}
