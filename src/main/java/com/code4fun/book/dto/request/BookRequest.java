package com.code4fun.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
    @NotBlank String name,
    @NotBlank String reading,
    @NotBlank String series,
    @NotNull Integer year,
    @NotNull Integer duration,
    @NotNull Integer quality) {}
