package com.code4fun.book;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = {BookApplication.class})
@ActiveProfiles("it")
@AutoConfigureMockMvc
@TestExecutionListeners(
    listeners = DbUnitTestExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@DbUnitConfiguration
@DatabaseTearDown("classpath:clear-data.xml")
public class BaseIntegrationTest {
  @Autowired protected MockMvc mockMvc;

  protected static final String PATH_TO_RESPONSE = "com/code4fun/book/integration/response";

  static PostgreSQLContainer<?> postgres;

  static {
    postgres = new PostgreSQLContainer<>("postgres:14.4-alpine");

    postgres.start();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }
}
