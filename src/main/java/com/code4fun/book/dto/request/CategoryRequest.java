package com.code4fun.book.dto.request;

import javax.validation.constraints.NotBlank;

public record CategoryRequest(@NotBlank String name) {}
