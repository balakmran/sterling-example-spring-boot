package dev.balakmran.example.web;

import dev.balakmran.example.api.model.BookResponse;
import dev.balakmran.example.catalog.Book;
import org.mapstruct.Mapper;

/** Maps catalog books to the generated API model. */
@Mapper(componentModel = "spring")
public interface BookMapper {
  BookResponse toResponse(Book book);
}
