package com.parallelxps.graphql.inventory.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.parallelxps.schema.inventory.tables.pojos.Brands;
import com.parallelxps.schema.inventory.tables.pojos.Categories;
import com.parallelxps.schema.inventory.tables.pojos.Keywords;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.parallelxps.schema.inventory.Tables.BRANDS;
import static com.parallelxps.schema.inventory.Tables.CATEGORIES;
import static com.parallelxps.schema.inventory.Tables.KEYWORDS;

@Component
@Transactional(readOnly = true)
public class Query implements GraphQLQueryResolver {
  private final DSLContext create;

  public Query(DSLContext create) {
    this.create = create;
  }

  public Brands brand(String id) {
    return create.selectFrom(BRANDS)
        .where(BRANDS.ID.eq(pk(id)))
        .fetchOptional()
        .map(r -> r.into(Brands.class))
        .orElse(null);
  }

  public Categories category(String id) {
    return create.selectFrom(CATEGORIES)
        .where(CATEGORIES.ID.eq(pk(id)))
        .fetchOptional()
        .map(r -> r.into(Categories.class))
        .orElse(null);
  }

  public Keywords keyword(String id) {
    return create.selectFrom(KEYWORDS)
        .where(KEYWORDS.ID.eq(pk(id)))
        .fetchOptional()
        .map(r -> r.into(Keywords.class))
        .orElse(null);
  }

  static Integer pk(String id) {
    return Integer.valueOf(id);
  }
}
