package com.parallelxps.graphql.inventory.data.sql;

import java.util.Optional;
import javax.annotation.Nullable;
import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class SpringTxExecuteListener extends DefaultExecuteListener {
  /** {@inheritDoc} */
  @Override
  public void exception(ExecuteContext ctx) {
    var opt = Optional.ofNullable(ctx.sqlException());

    opt.map(e -> ctx.dialect())
        .map(this::translator)
        .ifPresent(t -> ctx.exception(t.translate("jOOQ", ctx.sql(), opt.get())));
  }

  SQLExceptionTranslator translator(@Nullable SQLDialect dialect) {
    return Optional.ofNullable(dialect)
        .map(o -> o.thirdParty().springDbName())
        .map(name -> (SQLExceptionTranslator) new SQLErrorCodeSQLExceptionTranslator(name))
        .orElseGet(SQLStateSQLExceptionTranslator::new);
  }
}
