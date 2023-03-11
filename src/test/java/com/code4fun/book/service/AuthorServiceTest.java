package com.code4fun.book.service;

import com.code4fun.book.dto.mapper;
import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.exception.ErrorDetails;
import com.code4fun.book.exception.ResourceNotFoundException;
import com.code4fun.book.model.Author;
import com.code4fun.book.repository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static java.lang.Math.toIntExact;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    private static final ZonedDateTime NOW = ZonedDateTime.of(2022, 7, 22, 10, 10, 10, 0, ZoneId.systemDefault());
    private List<Author> authors;
    @Mock
    private AuthorRepository repository;
    @Mock
    private Clock clock;
    @InjectMocks
    private AuthorService service;

    @BeforeEach
    void init() {
        authors = new ArrayList<>(Arrays.asList(new Author("Foo", "Boo"), new Author("Steven", "Born"), new Author("Fillip", "Rain")));
        long i = 1L;
        for (Author author : authors) {
            author.setId(i++);
        }
    }

    @AfterEach
    void cleanUp() {
        authors.clear();
    }

    @Test
    void findByIdTest() {
        final long _id = 1L;
        final var _author = authors.get(toIntExact(_id));
        when(repository.findById(_id)).thenReturn(Optional.of(_author));
        final var _responseDto = mapper.authorToAuthorResponseDto(_author);

        assertEquals(_responseDto, service.findById(_id));
    }

    @Test
    void findAllTest() {
        final var _authorResponseDtos = mapper.authorsToAuthorResponseDtos(authors);
        when(repository.findAll()).thenReturn(authors);

        assertEquals(_authorResponseDtos, service.findAll());
    }

    @Test
    void saveTest() {
        final long _id = 1L;
        final var _author = authors.get(toIntExact(_id));
        when(repository.save(any(Author.class))).thenReturn(_author);
        final var _requestDto = AuthorRequestDto.builder().firstName("Steven").lastName("Born").build();
        final var _responseDto = mapper.authorToAuthorResponseDto(_author);

        assertEquals(_responseDto, service.save(_requestDto));
    }

    @Test
    void updateTest() {
        final var _author = spy(Author.class);
        _author.setId(1L);
        _author.setFirstName("Steven");
        _author.setLastName("Jobs");
        doReturn(Optional.of(_author)).when(repository).findById(1L);

        final var _requestDto = AuthorRequestDto.builder().id(1L).firstName("Steven").lastName("Born").build();
        final var _responseDto = AuthorResponseDto.builder().id(1L).firstName("Steven").lastName("Born").bookIds(Collections.emptySet()).build();

        assertEquals(service.update(_requestDto), _responseDto);
    }

    @Test
    void deleteTest() {
        final long _id = 1L;
        final var _author = authors.get(toIntExact(_id));
        when(repository.findById(_id)).thenReturn(Optional.of(_author));
        doNothing().when(repository).delete(_author);
        service.delete(_id);

        verify(repository, times(1)).findById(_id);
        verify(repository, times(1)).delete(_author);
    }

    @Test
    void getByIdTest() {
        final long _validId = 1L;
        final var _author = authors.get(toIntExact(_validId));
        when(repository.findById(_validId)).thenReturn(Optional.of(_author));

        assertEquals(_author, service.getById(_validId));

        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
        final long _invalidId = -1L;
        when(repository.findById(_invalidId)).thenReturn(Optional.empty());
        final var errorMessage = new ErrorDetails(LocalDateTime.now(clock), "Author Id", "Id", _invalidId).toString();

        try {
            service.getById(_invalidId);
        } catch (ResourceNotFoundException ex) {
            assertEquals(errorMessage, ex.getMessage());
        }

        verify(repository, times(2)).findById(anyLong());
    }
}