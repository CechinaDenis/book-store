package com.code4fun.book.dto.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorRequestDto {
  @NotBlank private String uuid;
  @NotBlank private String firstName;
  @NotBlank private String lastName;
}
