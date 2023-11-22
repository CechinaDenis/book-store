package com.code4fun.book.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(ErrorDetails details) {
    super(getMessage(details));
    log.warn(getMessage(details));
  }

  private static String getMessage(ErrorDetails details) {
    return String.format(
        "%s not found with %s : '%s' \n%s",
        details.resourceName(), details.fieldName(), details.fieldValue(), details.timestamp());
  }
}
