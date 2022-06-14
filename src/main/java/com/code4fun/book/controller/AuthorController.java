package com.code4fun.book.controller;

import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/authors", produces = "application/json; charset=utf-8")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/{id}")
    public AuthorResponseDto findById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @GetMapping
    public List<AuthorResponseDto> findAll() {
        return authorService.findAll();
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> add(@RequestBody AuthorRequestDto requestDto) {
        final var responseDto = authorService.save(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AuthorResponseDto> edit(@RequestBody AuthorRequestDto requestDto) {
        final var responseDto = authorService.update(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}