// package com.code4fun.book.controller;
//
// import com.code4fun.book.dto.requestDto.AuthorRequest;
// import com.code4fun.book.dto.responseDto.AuthorResponse;
// import com.code4fun.book.service.AuthorService;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
//
// import java.util.List;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.isA;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.when;
//
// @ExtendWith(MockitoExtension.class)
// class AuthorControllerTest {
// @Mock
// AuthorService authorService;
// @InjectMocks
// AuthorController authorController;
//
// @Test
// void findByIdTest() {
// final var _id = 1L;
// final var _authorResponseDto =
// AuthorResponse.builder().id(_id).firstName("Foo").lastName("Boo").build();
// when(authorService.findById(_id)).thenReturn(_authorResponseDto);
//
// assertEquals(_authorResponseDto, authorController.findById(_id));
// }
//
// @Test
// void findAllTest() {
// final var _authorResponseDtos = List.of(
// AuthorResponse.builder().id(1L).firstName("Foo").lastName("Boo").build(),
// AuthorResponse.builder().id(2L).firstName("Steven").lastName("Born").build(),
// AuthorResponse.builder().id(3L).firstName("Fillip").lastName("Rain").build()
// );
//// when(authorService.findAll()).thenReturn(_authorResponseDtos);
//
//// assertEquals(_authorResponseDtos, authorController.findAll());
// }
//
// @Test
// void addTest() {
// final var _requestDto =
// AuthorRequest.builder().firstName("James").lastName("Bond").build();
// final var _responseDto =
// AuthorResponse.builder().id(1L).firstName("James").lastName("Bond").build();
// when(authorService.save(_requestDto)).thenReturn(_responseDto);
//
// assertEquals(new ResponseEntity<>(_responseDto, HttpStatus.CREATED),
// authorController.add(_requestDto));
// }
//
// @Test
// void editTest() {
// final var _requestDto =
// AuthorRequest.builder().id(1L).firstName("James").lastName("Bond").build();
// final var _responseDto =
// AuthorResponse.builder().id(1L).firstName("James").lastName("Bond").build();
// when(authorService.update(_requestDto)).thenReturn(_responseDto);
//
// assertEquals(_responseDto, authorController.edit(_requestDto));
// }
//
// @Test
// void deleteTest() {
// doNothing().when(authorService).delete(isA(Long.class));
// assertEquals(new ResponseEntity<>(HttpStatus.OK),
// authorController.delete(1L));
// }
// }
