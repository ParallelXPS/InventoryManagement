package com.parallelxps.graphql.inventory.data.sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.jooq.exception.DataAccessException;
import org.junit.jupiter.api.Test;

public class SpringTxConnectionProviderTest {
  @Test
  public void shouldThrowOnAcquire() throws SQLException {
    var ds = mock(DataSource.class);

    when(ds.getConnection()).thenThrow(new SQLException());

    var provider = provider(ds);

    assertThrows(DataAccessException.class, provider::acquire);
  }

  @Test
  public void shouldThrowOnRelease() throws SQLException {
    var ds = mock(DataSource.class);
    var conn = mock(Connection.class);

    doThrow(new SQLException()).when(conn).close();

    var provider = provider(ds);

    assertThrows(DataAccessException.class, () -> provider.release(conn));
  }

  @Test
  public void shouldAcquireAndRelease() throws SQLException {
    var ds = mock(DataSource.class);
    var conn = mock(Connection.class);

    when(ds.getConnection()).thenReturn(conn);

    var provider = provider(ds);

    assertEquals(conn, provider.acquire());
    provider.release(conn);
    verify(conn).close();
  }

  private SpringTxConnectionProvider provider(DataSource ds) {
    return new SpringTxConnectionProvider(ds);
  }
}
