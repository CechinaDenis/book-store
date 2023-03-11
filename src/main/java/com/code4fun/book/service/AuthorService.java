package com.code4fun.book.service;

import com.code4fun.book.dto.mapper;
import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.exception.ErrorDetails;
import com.code4fun.book.exception.ResourceNotFoundException;
import com.code4fun.book.model.Author;
import com.code4fun.book.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorService implements MyService<AuthorRequestDto, AuthorResponseDto, Long> {
    private final AuthorRepository authorRepository;
    private final Clock clock;

    @Override
    public AuthorResponseDto findById(Long id) {
        log.info("Getting Author by ID: {}", id);
        return mapper.authorToAuthorResponseDto(getById(id));
    }

    @Override
    public List<AuthorResponseDto> findAll() {
        log.info("Getting all Authors");
        return mapper.authorsToAuthorResponseDtos(authorRepository.findAll());
    }

    @Override
    public AuthorResponseDto save(AuthorRequestDto requestDto) {
        log.info("Saving  new Author: {}", requestDto);
        final var _author = mapper.authorRequestDtoToAuthor(requestDto);
        return mapper.authorToAuthorResponseDto(authorRepository.save(_author));
    }

    @Transactional
    @Override
    public AuthorResponseDto update(AuthorRequestDto requestDto) {
        final var authorId = requestDto.getId();
        log.info("Updating Author with ID: {}", authorId);
        var _author = this.getById(authorId);
        _author.setFirstName(requestDto.getFirstName());
        _author.setLastName(requestDto.getLastName());
        return mapper.authorToAuthorResponseDto(_author);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Author with ID: {}", id);
        final var _author = this.getById(id);
        authorRepository.delete(_author);
    }

    Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> {
                    final var details = new ErrorDetails(LocalDateTime.now(clock), "Author Id", "Id", id);
                    return new ResourceNotFoundException(details);
                });
    }
}