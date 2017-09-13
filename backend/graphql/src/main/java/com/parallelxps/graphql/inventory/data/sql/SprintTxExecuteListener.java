package com.parallelxps.graphql.inventory.data.sql;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import java.sql.SQLException;

public class SprintTxExecuteListener extends DefaultExecuteListener {
  /**
   * {@inheritDoc}
   */
  @Override
  public void exception(ExecuteContext ctx) {
    SQLException e = ctx.sqlException();

    if (e != null) {
      SQLDialect dialect = ctx.dialect();
      SQLExceptionTranslator translator = (dialect != null)
          ? new SQLErrorCodeSQLExceptionTranslator(dialect.thirdParty().springDbName())
          : new SQLStateSQLExceptionTranslator();

      ctx.exception(translator.translate("jOOQ", ctx.sql(), e));
    }
  }
}
