package com.code4fun.book.dto.request;

import javax.validation.constraints.NotBlank;

public record AuthorRequest(String id, @NotBlank String firstName, @NotBlank String lastName) {}
