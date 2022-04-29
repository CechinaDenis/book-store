package com.code4fun.book.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorDetails {
    private final Date timestamp;
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;
}