package com.code4fun.book.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record BookRequest(
    @NotBlank String name,
    @NotBlank String reading,
    @NotBlank String series,
    @NotNull Integer year,
    @NotNull Integer duration,
    @NotNull Integer quality) {}
