package com.code4fun.book.controller;

import com.code4fun.book.dto.request.AuthorRequest;
import com.code4fun.book.dto.response.AuthorResponse;
import com.code4fun.book.service.AuthorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/authors")
public class AuthorController {
  private final AuthorService authorService;

  @GetMapping("/{id}")
  public AuthorResponse findById(
      @PathVariable @NotBlank(message = "ID must not be blank.") String id) {
    return authorService.findById(id);
  }

  @GetMapping
  public Page<AuthorResponse> findAll(@PageableDefault Pageable pageable) {
    return authorService.findAll(pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AuthorResponse create(@Valid @RequestBody AuthorRequest request) {
    return authorService.save(request);
  }

  @PutMapping("/{id}")
  public AuthorResponse update(
      @PathVariable @NotBlank(message = "ID must not be blank.") String id,
      @Valid @RequestBody AuthorRequest request) {
    return authorService.update(id, request);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable @NotBlank(message = "ID must not be blank.") String id) {
    authorService.delete(id);
  }
}
