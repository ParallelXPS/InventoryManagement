package com.parallelxps.graphql.inventory.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.parallelxps.schema.inventory.tables.pojos.*;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        .fetchOptionalInto(Artists.class)
        .orElse(null);
  }

  public Brands brand(Items item) {
    return create.selectFrom(BRANDS)
        .where(BRANDS.ID.eq(item.getBrandId()))
        .fetchOptionalInto(Brands.class)
        .orElse(null);
  }

  public List<Categories> categories(Items item) {
    return java.util.Collections.emptyList();
  }

  public Geometries geometry(Items item) {
    return create.selectFrom(GEOMETRIES)
        .where(GEOMETRIES.ID.eq(item.getGeometryId()))
        .fetchOptionalInto(Geometries.class)
        .orElse(null);
  }

  public Mappings mapping(Items item) {
    return create.selectFrom(MAPPINGS)
        .where(MAPPINGS.ID.eq(item.getMappingId()))
        .fetchOptionalInto(Mappings.class)
        .orElse(null);
  }
}
