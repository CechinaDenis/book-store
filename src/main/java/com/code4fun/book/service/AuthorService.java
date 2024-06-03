package com.code4fun.book.service;

import com.code4fun.book.dto.request.AuthorRequest;
import com.code4fun.book.dto.response.AuthorResponse;
import com.code4fun.book.mapper.AuthorMapper;
import com.code4fun.book.model.Author;
import com.code4fun.book.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AuthorService {
  private final AuthorRepository authorRepository;
  private final AuthorMapper mapper;

  public AuthorResponse findById(@NotBlank String id) {
    log.info("Getting Author by ID: {}", id);
    return mapper.map(getById(id));
  }

  public Page<AuthorResponse> findAll(Pageable pageable) {
    log.info("Getting all Authors");
    return mapper.map(authorRepository.findAll(pageable));
  }

  @Transactional
  public AuthorResponse save(@Valid AuthorRequest request) {
    log.info("Saving  new Author: {}", request);
    final var author = mapper.map(request);

    return mapper.map(authorRepository.save(author));
  }

  @Transactional
  public AuthorResponse update(@NotBlank String id, @Valid AuthorRequest request) {
    log.info("Updating Author with ID: {}", id);
    final var author = this.getById(id);
    author.setFirstName(request.firstName());
    author.setLastName(request.lastName());

    return mapper.map(author);
  }

  @Transactional
  public void delete(@NotBlank String id) {
    final var author = getById(id);
    log.info("Deleting Author with ID: {}", id);
    author.getBooks().forEach(b -> b.removeAuthor(author));
    authorRepository.delete(author);
  }

  Author getById(String id) {
    return authorRepository
        .findById(id)
        .orElseThrow(
            () -> {
              final var message = String.format("Author with id: '%s' was not found.", id);
              log.warn(message);

              return new EntityNotFoundException(message);
            });
  }
}
