package com.parallelxps.graphql.inventory.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.parallelxps.schema.inventory.tables.pojos.Categories;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.parallelxps.schema.inventory.Tables.CATEGORIES;

@Component
@Transactional(readOnly = true)
public class QueryResolver implements GraphQLQueryResolver {
  private final DSLContext create;

  public QueryResolver(DSLContext create) {
    this.create = create;
  }

  public Categories category(String id) {
    Record record = create.selectFrom(CATEGORIES)
        .where(CATEGORIES.ID.eq(Integer.valueOf(id)))
        .fetchOne();

    return Optional.ofNullable(record)
        .map(r -> r.into(Categories.class))
        .orElse(null);
  }
}
