package com.parallelxps.graphql.inventory.resolvers;

import static com.parallelxps.graphql.inventory.data.sql.JooqConfiguration.JooqConfiguration;

import javax.inject.Inject;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class QueryTest {
  @Inject private Query query;

  @Test
  public void foo() {
    System.out.println("AWZ >>>>> " + query.category("1"));
  }

  @Configuration
  @EnableTransactionManagement
  static class Config {
    @Bean
    public DSLContext create(DataSource dataSource) {
      return DSL.using(JooqConfiguration.configuration(dataSource, SQLDialect.H2));
    }

    @Bean
    public DataSource dataSource() {
      return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
      return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public Query query(DSLContext create) {
      return new Query(create);
    }

    @Bean
    public SpringLiquibase springLiquibase(DataSource dataSource) {
      var bean = new SpringLiquibase();

      bean.setChangeLog("classpath:db/changelog/db.changelog-master.yaml");
      bean.setDataSource(dataSource);

      return bean;
    }
  }
}
