package com.parallelxps.graphql.inventory.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class QueryResolver implements GraphQLQueryResolver {
  private final DSLContext create;

  public QueryResolver(DSLContext create) {
    this.create = create;
  }
}
