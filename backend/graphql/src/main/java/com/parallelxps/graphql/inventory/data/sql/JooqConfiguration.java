package com.parallelxps.graphql.inventory.data.sql;

import javax.sql.DataSource;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListenerProvider;

public enum JooqConfiguration {
  JooqConfiguration;

  public Configuration configuration(DataSource dataSource, SQLDialect dialect) {
    return new DefaultConfiguration()
        .set(new SpringTxConnectionProvider(dataSource))
        .set(dialect)
        .set(new Settings().withRenderSchema(false))
        .set(new DefaultExecuteListenerProvider(new SpringTxExecuteListener()));
  }
}
