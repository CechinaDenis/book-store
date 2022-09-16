package com.code4fun.book.exception;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ErrorDetails {
    private final LocalDateTime timestamp;
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    @Override
    public String toString() {
        return String.format("%s not found with %s : '%s' \n%s",
                this.resourceName, this.fieldName, this.fieldValue, this.timestamp);
    }
}