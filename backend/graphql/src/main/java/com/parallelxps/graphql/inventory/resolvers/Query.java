package com.parallelxps.graphql.inventory.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.parallelxps.schema.inventory.tables.pojos.Categories;
import graphql.schema.DataFetchingEnvironment;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.parallelxps.schema.inventory.Tables.CATEGORIES;

@Component
@Transactional(readOnly = true)
public class Query implements GraphQLQueryResolver {
  private final DSLContext create;

  public Query(DSLContext create) {
    this.create = create;
  }

  public Categories category(String id, DataFetchingEnvironment env) {
    return create.selectFrom(CATEGORIES)
        .where(CATEGORIES.ID.eq(Integer.valueOf(id)))
        .fetchOptional()
        .map(r -> r.into(Categories.class))
        .orElse(null);
  }
}
