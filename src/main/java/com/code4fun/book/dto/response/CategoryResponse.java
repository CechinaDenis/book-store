package com.code4fun.book.dto.response;

import java.util.Set;

public record CategoryResponse(String id, String name, Set<String> bookIds) {}
