package com.code4fun.book.dto.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorRequestDto {
  private String uuid;
  private String firstName;
  private String lastName;
}