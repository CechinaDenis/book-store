package com.code4fun.book.dto.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequestDto {
  @NotBlank private String uuid;
  @NotBlank private String name;
}
