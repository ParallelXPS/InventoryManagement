package com.parallelxps.graphql.inventory.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.parallelxps.schema.inventory.tables.pojos.*;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.parallelxps.schema.inventory.Tables.*;

@Component
@Transactional(readOnly = true)
public class ItemResolver implements GraphQLResolver<Items> {
  private final DSLContext create;

  public ItemResolver(DSLContext create) {
    this.create = create;
  }

  public Artists artist(Items item) {
    return create.selectFrom(ARTISTS)
        .where(ARTISTS.ID.eq(item.getArtistId()))
        .fetchOptional()
        .map(r -> r.into(Artists.class))
        .orElse(null);
  }

  public Brands brand(Items item) {
    return create.selectFrom(BRANDS)
        .where(BRANDS.ID.eq(item.getBrandId()))
        .fetchOptional()
        .map(r -> r.into(Brands.class))
        .orElse(null);
  }

  public Geometries geometry(Items item) {
    return create.selectFrom(GEOMETRIES)
        .where(GEOMETRIES.ID.eq(item.getGeometryId()))
        .fetchOptional()
        .map(r -> r.into(Geometries.class))
        .orElse(null);
  }

  public Mappings mapping(Items item) {
    return create.selectFrom(MAPPINGS)
        .where(MAPPINGS.ID.eq(item.getMappingId()))
        .fetchOptional()
        .map(r -> r.into(Mappings.class))
        .orElse(null);
  }
}
