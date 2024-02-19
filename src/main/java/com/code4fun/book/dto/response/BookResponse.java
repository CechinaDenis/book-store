package com.code4fun.book.dto.response;

import java.util.Set;

public record BookResponse(
    String id,
    String name,
    String reading,
    String series,
    Integer year,
    Integer duration,
    Integer quality,
    Set<String> authorIds,
    Set<String> categoryIds) {}
