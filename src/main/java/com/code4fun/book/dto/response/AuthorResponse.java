package com.code4fun.book.dto.response;

import java.util.Set;

public record AuthorResponse(String id, String firstName, String lastName, Set<String> bookIds) {}
