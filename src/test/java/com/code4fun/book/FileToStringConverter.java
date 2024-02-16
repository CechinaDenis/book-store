package com.code4fun.book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileToStringConverter {
  public static String getContent(String filePath) throws IOException {
    return Files.readString(Path.of(new ClassPathResource(filePath).getURI()));
  }
}
