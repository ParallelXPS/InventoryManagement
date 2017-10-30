package com.parallelxps.graphql.inventory.data.fields;

import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Field;
import org.jooq.TableLike;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static com.parallelxps.schema.inventory.Tables.ITEMS;

@SuppressWarnings("ImmutableEnumChecker")
public enum FieldMapper {
  Items(ITEMS);

  private final Map<String, Field<?>> mapper;

  private FieldMapper(TableLike<?> table) {
    this.mapper = Arrays.stream(table.fields())
        .collect(Collectors.toMap(this::normalize, Function.identity()));
  }

  public List<Field<?>> fields(DataFetchingEnvironment env) {
    return env.getSelectionSet().get().keySet().stream()
        .map(mapper::get)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private String normalize(Field<?> field) {
    String name = StringUtils.removeEnd(field.getName(), "_ID");

    return UPPER_UNDERSCORE.to(LOWER_CAMEL, name);
  }
}
