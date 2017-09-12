package com.parallelxps.graphql.inventory.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.parallelxps.schema.inventory.tables.pojos.Categories;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {
  public Categories category(String id) {
    return new Categories(Integer.valueOf(id));
  }
}
