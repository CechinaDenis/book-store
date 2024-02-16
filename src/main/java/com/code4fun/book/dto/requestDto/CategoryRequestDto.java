package com.code4fun.book.dto.requestDto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDto {
  @NotBlank private String name;
}
