package com.code4fun.book.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(@NotBlank String name) {}
