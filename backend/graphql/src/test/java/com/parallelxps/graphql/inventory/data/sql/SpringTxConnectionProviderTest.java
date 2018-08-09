package com.parallelxps.graphql.inventory.data.sql;

import org.jooq.exception.DataAccessException;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SpringTxConnectionProviderTest {
  @Test(expected = DataAccessException.class)
  public void shouldThrowOnAcquire() throws SQLException {
    var ds = mock(DataSource.class);

    when(ds.getConnection()).thenThrow(new SQLException());

    provider(ds).acquire();
  }

  @Test(expected = DataAccessException.class)
  public void shouldThrowOnRelease() throws SQLException {
    var ds = mock(DataSource.class);
    var conn = mock(Connection.class);

    doThrow(new SQLException()).when(conn).close();

    provider(ds).release(conn);
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
