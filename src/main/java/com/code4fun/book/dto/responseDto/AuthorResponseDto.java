package com.code4fun.book.dto.responseDto;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorResponseDto {
  private String id;
  private String firstName;
  private String lastName;
  private Set<String> bookIds;
}
