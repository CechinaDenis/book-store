package com.code4fun.book;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("it")
@AutoConfigureMockMvc
@TestExecutionListeners(
    listeners = DbUnitTestExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@DatabaseTearDown("classpath:clear-data.xml")
public class BaseIntegrationTest {
  @Autowired protected MockMvc mockMvc;

  protected static final String PATH_TO_RESPONSE = "com/code4fun/book/integration/response";
}
