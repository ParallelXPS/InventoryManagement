package com.parallelxps.graphql.inventory.data.sql;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class SpringTxExecuteListenerTest {
  @Test
  public void shouldHandleNullSQLDialect() {
    assertThat(listener().translator(null), instanceOf(SQLStateSQLExceptionTranslator.class));
  }

  @Test
  public void shouldHandleNonNullSQLDialect() {
    assertThat(
        listener().translator(SQLDialect.H2), instanceOf(SQLErrorCodeSQLExceptionTranslator.class));
  }

  @Test
  public void shouldHandleNullSQLException() {
    var ctx = mock(ExecuteContext.class);

    listener().exception(ctx);

    verify(ctx, never()).exception(any(RuntimeException.class));
  }

  @Test
  public void shouldHandleNonNullSQLException() {
    var ctx = mock(ExecuteContext.class);

    when(ctx.dialect()).thenReturn(SQLDialect.H2);
    when(ctx.sqlException()).thenReturn(new SQLException());

    listener().exception(ctx);

    verify(ctx).exception(any(RuntimeException.class));
  }

  private SpringTxExecuteListener listener() {
    return new SpringTxExecuteListener();
  }
}
