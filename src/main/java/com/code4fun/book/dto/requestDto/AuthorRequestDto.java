package com.code4fun.book.dto.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequestDto {
  private String id;
  @NotBlank private String firstName;
  @NotBlank private String lastName;
}
