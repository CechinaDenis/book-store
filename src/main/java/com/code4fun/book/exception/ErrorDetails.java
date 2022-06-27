package com.code4fun.book.exception;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class ErrorDetails {
    private final Date timestamp;
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;
}