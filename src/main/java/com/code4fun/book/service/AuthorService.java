package com.code4fun.book.service;

import com.code4fun.book.dto.mapper;
import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.exception.ErrorDetails;
import com.code4fun.book.exception.ResourceNotFoundException;
import com.code4fun.book.model.Author;
import com.code4fun.book.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService implements MyService<AuthorRequestDto, AuthorResponseDto, Long> {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponseDto findById(Long id) {
        return mapper.authorToAuthorResponseDto(getById(id));
    }

    @Override
    public List<AuthorResponseDto> findAll() {
        return mapper.authorsToAuthorResponseDtos(authorRepository.findAll());
    }

    @Override
    public AuthorResponseDto save(AuthorRequestDto requestDto) {
        final var author = mapper.authorRequestDtoToAuthor(requestDto);
        return mapper.authorToAuthorResponseDto(authorRepository.save(author));
    }

    @Transactional
    @Override
    public AuthorResponseDto update(AuthorRequestDto requestDto) {
        var author = this.getById(requestDto.getId());
        author.setFirstName(requestDto.getFirstName());
        author.setLastName(requestDto.getLastName());
        return mapper.authorToAuthorResponseDto(author);
    }

    @Override
    public void delete(Long id) {
        final var _author = this.getById(id);
        authorRepository.delete(_author);
    }

    Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> {
                    final ErrorDetails details = new ErrorDetails(new Date(), "Author Id", "Id", id);
                    return new ResourceNotFoundException(details);
                });
    }
}