package com.code4fun.book.controller;

import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.service.AuthorService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/authors")
public class AuthorController {
  private final AuthorService authorService;

  @GetMapping("/{id}")
  public AuthorResponseDto findById(@PathVariable @NotBlank String id) {
    return authorService.findById(id);
  }

  @GetMapping
  public Page<AuthorResponseDto> findAll(@PageableDefault Pageable pageable) {
    return authorService.findAll(pageable);
  }

  @PostMapping
  public AuthorResponseDto create(@Valid @RequestBody AuthorRequestDto requestDto) {
    return authorService.save(requestDto);
  }

  @PutMapping
  public AuthorResponseDto edit(@Valid @RequestBody AuthorRequestDto requestDto) {
    return authorService.update(requestDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable @NotBlank String id) {
    authorService.delete(id);
  }
}
