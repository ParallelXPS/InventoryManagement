package com.parallelxps.graphql.inventory.resolvers;

import static com.parallelxps.schema.inventory.Tables.*;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.parallelxps.graphql.inventory.data.fields.FieldMapper;
import com.parallelxps.schema.inventory.tables.pojos.*;
import graphql.schema.DataFetchingEnvironment;
import javax.annotation.Nullable;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class Query implements GraphQLQueryResolver {
  private final DSLContext create;

  public Query(DSLContext create) {
    this.create = create;
  }

  static Integer pk(String id) {
    return Integer.valueOf(id);
  }

  @Nullable
  public Artists artist(String id) {
    return create
        .selectFrom(ARTISTS)
        .where(ARTISTS.ID.eq(pk(id)))
        .fetchOptionalInto(Artists.class)
        .orElse(null);
  }

  @Nullable
  public Brands brand(String id) {
    return create
        .selectFrom(BRANDS)
        .where(BRANDS.ID.eq(pk(id)))
        .fetchOptionalInto(Brands.class)
        .orElse(null);
  }

  @Nullable
  public Categories category(String id) {
    return create
        .selectFrom(CATEGORIES)
        .where(CATEGORIES.ID.eq(pk(id)))
        .fetchOptionalInto(Categories.class)
        .orElse(null);
  }

  @Nullable
  public Geometries geometry(String id) {
    return create
        .selectFrom(GEOMETRIES)
        .where(GEOMETRIES.ID.eq(pk(id)))
        .fetchOptionalInto(Geometries.class)
        .orElse(null);
  }

  @Nullable
  public Items item(String id, DataFetchingEnvironment env) {
    return create
        .select(FieldMapper.Items.fields(env))
        .from(ITEMS)
        .where(ITEMS.ID.eq(pk(id)))
        .fetchOptionalInto(Items.class)
        .orElse(null);
  }

  @Nullable
  public Keywords keyword(String id) {
    return create
        .selectFrom(KEYWORDS)
        .where(KEYWORDS.ID.eq(pk(id)))
        .fetchOptionalInto(Keywords.class)
        .orElse(null);
  }

  @Nullable
  public Mappings mapping(String id) {
    return create
        .selectFrom(MAPPINGS)
        .where(MAPPINGS.ID.eq(pk(id)))
        .fetchOptionalInto(Mappings.class)
        .orElse(null);
  }
}
