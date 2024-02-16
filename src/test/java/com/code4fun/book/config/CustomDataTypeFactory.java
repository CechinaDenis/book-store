package com.code4fun.book.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CustomDataTypeFactory {

  private final DataSource dataSource;

  @Bean
  public DatabaseConfigBean dbUnitDatabaseConfig() {
    var dbConfig = new DatabaseConfigBean();
    dbConfig.setDatatypeFactory(new PostgresqlDataTypeFactory());
    return dbConfig;
  }

  @Bean
  public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection() {
    var dbConnection = new DatabaseDataSourceConnectionFactoryBean(dataSource);
    dbConnection.setDatabaseConfig(dbUnitDatabaseConfig());
    return dbConnection;
  }
}
