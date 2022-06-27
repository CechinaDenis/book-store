package com.code4fun.book.controller;

import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {
    @Mock
    AuthorService authorService;
    @InjectMocks
    AuthorController authorController;

    @Test
    void findByIdTest() {
        final var id = 1L;
        final var authorResponseDto = AuthorResponseDto.builder().id(id).firstName("Foo").lastName("Boo").build();
        when(authorService.findById(id)).thenReturn(authorResponseDto);

        assertEquals(authorResponseDto, authorController.findById(id));
    }

    @Test
    void findAllTest() {
        final var authorResponseDtos = List.of(
                AuthorResponseDto.builder().id(1L).firstName("Foo").lastName("Boo").build(),
                AuthorResponseDto.builder().id(2L).firstName("Steven").lastName("Born").build(),
                AuthorResponseDto.builder().id(3L).firstName("Fillip").lastName("Rain").build()
        );
        when(authorService.findAll()).thenReturn(authorResponseDtos);

        assertEquals(authorResponseDtos, authorController.findAll());
    }

    @Test
    void addTest() {
        final var requestDto = AuthorRequestDto.builder().firstName("James").lastName("Bond").build();
        final var responseDto = AuthorResponseDto.builder().id(1L).firstName("James").lastName("Bond").build();
        when(authorService.save(requestDto)).thenReturn(responseDto);

        assertEquals(new ResponseEntity<>(responseDto, HttpStatus.CREATED), authorController.add(requestDto));
    }

    @Test
    void editTest() {
        final var requestDto = AuthorRequestDto.builder().id(1L).firstName("James").lastName("Bond").build();
        final var responseDto = AuthorResponseDto.builder().id(1L).firstName("James").lastName("Bond").build();
        when(authorService.update(requestDto)).thenReturn(responseDto);

        assertEquals(new ResponseEntity<>(responseDto, HttpStatus.OK), authorController.edit(requestDto));
    }

    @Test
    void deleteTest() {
        doNothing().when(authorService).delete(isA(Long.class));
        assertEquals(new ResponseEntity<>(HttpStatus.OK), authorController.delete(1L));
    }
}