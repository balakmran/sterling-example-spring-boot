package dev.balakmran.example.catalog;

import lombok.Builder;
import lombok.Value;
import org.jspecify.annotations.Nullable;

/** A book in the catalog. */
@Value
@Builder
public class Book {
  String isbn;
  String title;
  @Nullable String subtitle;
}
