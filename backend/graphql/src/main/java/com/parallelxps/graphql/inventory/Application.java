package com.parallelxps.graphql.inventory;

import static com.parallelxps.graphql.inventory.data.sql.JooqConfiguration.JooqConfiguration;

import javax.sql.DataSource;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public Configuration configuration(
      DataSource dataSource, @Value("${spring.jooq.sql-dialect}") String dialect) {
    return JooqConfiguration.configuration(dataSource, SQLDialect.valueOf(dialect));
  }
}
