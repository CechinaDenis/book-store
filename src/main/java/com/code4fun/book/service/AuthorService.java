package com.code4fun.book.service;

import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.mapper.AuthorMapper;
import com.code4fun.book.model.Author;
import com.code4fun.book.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

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

  public AuthorResponseDto save(AuthorRequestDto requestDto) {
    log.info("Saving  new Author: {}", requestDto);
    final var author = mapper.map(requestDto);
    return mapper.map(authorRepository.save(author));
  }

  @Transactional
  public AuthorResponseDto update(AuthorRequestDto requestDto) {
    final var authorId = requestDto.getUuid();
    log.info("Updating Author with ID: {}", authorId);
    var author = this.getById(authorId);
    author.setFirstName(requestDto.getFirstName());
    author.setLastName(requestDto.getLastName());
    return mapper.map(author);
  }

  public void delete(String id) {
    log.info("Deleting Author with ID: {}", id);
    final var author = this.getById(id);
    authorRepository.delete(author);
  }

  Author getById(String id) {
    return authorRepository.findById(id)
        .orElseThrow(() -> {
          var errorMessage = String.format("Author with uuid:'%s' was not found.", id);
          log.warn(errorMessage);
          return new EntityNotFoundException(errorMessage);
        });
  }
}