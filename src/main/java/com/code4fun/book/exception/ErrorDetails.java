package com.code4fun.book.exception;

import java.time.LocalDateTime;

public record ErrorDetails(
    LocalDateTime timestamp, String resourceName, String fieldName, Object fieldValue) {
  @Override
  public String toString() {
    return String.format(
        "%s not found with %s : '%s' \n%s",
        this.resourceName, this.fieldName, this.fieldValue, this.timestamp);
  }
}
