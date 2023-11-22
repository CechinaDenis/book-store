package com.code4fun.book.integration;

import com.code4fun.book.BookApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = {BookApplication.class})
@ActiveProfiles("it")
@AutoConfigureMockMvc
public class BaseIntegrationTest {
  @Autowired protected MockMvc mockMvc;
}
