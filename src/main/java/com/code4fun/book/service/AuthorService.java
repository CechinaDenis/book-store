package com.code4fun.book.service;

import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.mapper.AuthorMapper;
import com.code4fun.book.model.Author;
import com.code4fun.book.repository.AuthorRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorService {
  private final AuthorRepository authorRepository;
  private final AuthorMapper mapper;

  public AuthorResponseDto findById(String id) {
    log.info("Getting Author by ID: {}", id);
    return mapper.map(getById(id));
  }

  public Page<AuthorResponseDto> findAll(Pageable pageable) {
    log.info("Getting all Authors");
    return mapper.map(authorRepository.findAll(pageable));
  }

  @Transactional
  public AuthorResponseDto save(AuthorRequestDto requestDto) {
    log.info("Saving  new Author: {}", requestDto);
    final var author = mapper.map(requestDto);

    return mapper.map(authorRepository.save(author));
  }

  @Transactional
  public AuthorResponseDto update(String id, AuthorRequestDto requestDto) {
    log.info("Updating Author with ID: {}", id);
    final var author = this.getById(id);
    author.setFirstName(requestDto.getFirstName());
    author.setLastName(requestDto.getLastName());

    return mapper.map(author);
  }

  @Transactional
  public void delete(String id) {
    log.info("Deleting Author with ID: {}", id);
    final var author = getById(id);
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
