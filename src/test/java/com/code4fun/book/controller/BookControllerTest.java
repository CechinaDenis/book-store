package com.code4fun.book.controller;

import com.code4fun.book.dto.requestDto.BookRequestDto;
import com.code4fun.book.dto.responseDto.BookResponseDto;
import com.code4fun.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @Mock
    BookService bookService;
    @InjectMocks
    BookController bookController;

    @Test
    void findById() {
        final var responseDto = BookResponseDto
                .builder()
                .id(1L)
                .name("Eye of the World")
                .reading("Stevin Rodrigo")
                .series("Wheel of Time")
                .year(1999)
                .duration(45)
                .quality(56)
                .build();
        when(bookService.findById(1L)).thenReturn(responseDto);

        assertEquals(responseDto, bookController.findById(1L));
    }

    @Test
    void findAll() {
        final var responseDtos = List.of(
                BookResponseDto
                        .builder()
                        .id(1L)
                        .name("Eye of the World")
                        .reading("Stevin Rodrigo")
                        .series("Wheel of Time")
                        .year(1999)
                        .duration(45)
                        .quality(56)
                        .build(),
                BookResponseDto
                        .builder()
                        .id(2L)
                        .name("The Great Hunt")
                        .reading("Stevin Rodrigo")
                        .series("Wheel of Time")
                        .year(2002)
                        .duration(45)
                        .quality(56)
                        .build(),
                BookResponseDto
                        .builder()
                        .id(3L)
                        .name("The Dragon Reborn")
                        .reading("Stevin Rodrigo")
                        .series("Wheel of Time")
                        .year(2004)
                        .duration(45)
                        .quality(56)
                        .build(),
                BookResponseDto
                        .builder()
                        .id(4L)
                        .name("The Shadow Rising")
                        .reading("Stevin Rodrigo")
                        .series("Wheel of Time")
                        .year(2006)
                        .duration(45)
                        .quality(56)
                        .build()
        );
        when(bookService.findAll()).thenReturn(responseDtos);

        assertEquals(responseDtos, bookController.findAll());
    }

    @Test
    void add() {
        final var requestDto = BookRequestDto
                .builder()
                .name("Eye of the World")
                .reading("Stevin Rodrigo")
                .series("Wheel of Time")
                .year(1999)
                .duration(45)
                .quality(56)
                .build();
        final var responseDto = BookResponseDto
                .builder()
                .id(1L)
                .name("Eye of the World")
                .reading("Stevin Rodrigo")
                .series("Wheel of Time")
                .year(1999)
                .duration(45)
                .quality(56)
                .build();
        when(bookService.save(requestDto)).thenReturn(responseDto);

        assertEquals(new ResponseEntity<>(responseDto, HttpStatus.CREATED), bookController.add(requestDto));
    }

    @Test
    void addAuthorToBook() {
        final var responseDto = BookResponseDto
                .builder()
                .id(1L)
                .name("Eye of the World")
                .reading("Stevin Rodrigo")
                .series("Wheel of Time")
                .year(1999)
                .duration(45)
                .quality(56)
                .authorIds(Set.of(1L, 2L))
                .build();
        when(bookService.addAuthor(1L, 2L)).thenReturn(responseDto);

        assertEquals(responseDto, bookController.addAuthorToBook(1L, 2L));
    }

    @Test
    void addCategoryToBook() {
        final var responseDto = BookResponseDto
                .builder()
                .id(1L)
                .name("Eye of the World")
                .reading("Stevin Rodrigo")
                .series("Wheel of Time")
                .year(1999)
                .duration(45)
                .quality(56)
                .categoryIds(Set.of(1L, 2L))
                .build();
        when(bookService.addCategory(1L, 2L)).thenReturn(responseDto);

        assertEquals(responseDto, bookController.addCategoryToBook(1L, 2L));
    }

    @Test
    void edit() {
        final var requestDto = BookRequestDto.builder()
                .id(1L)
                .name("Eye of the World")
                .reading("Stevin Rodrigo")
                .series("Wheel of Time")
                .year(1999)
                .duration(45)
                .quality(56)
                .build();
        final var responseDto = BookResponseDto
                .builder()
                .id(1L)
                .name("Eye of the World")
                .reading("Stevin Rodrigo")
                .series("Wheel of Time")
                .year(1999)
                .duration(45)
                .quality(56)
                .build();
        when(bookService.update(requestDto)).thenReturn(responseDto);

        assertEquals(responseDto, bookController.edit(requestDto));
    }

    @Test
    void delete() {
        doNothing().when(bookService).delete(isA(Long.class));

        assertEquals(new ResponseEntity<>(HttpStatus.OK), bookController.delete(1L));
    }

    @Test
    void removeAuthorFromBook() {
        doNothing().when(bookService).removeAuthor(isA(Long.class), isA(Long.class));

        assertEquals(new ResponseEntity<>(HttpStatus.OK), bookController.removeAuthorFromBook(1L, 2L));
    }

    @Test
    void removeCategoryFromBook() {
        doNothing().when(bookService).removeCategory(isA(Long.class), isA(Long.class));

        assertEquals(new ResponseEntity<>(HttpStatus.OK), bookController.removeCategoryFromBook(1L, 2L));
    }
}