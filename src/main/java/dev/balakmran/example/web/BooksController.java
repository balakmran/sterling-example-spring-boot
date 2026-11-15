package dev.balakmran.example.web;

import dev.balakmran.example.api.BooksApi;
import dev.balakmran.example.api.model.BookRequest;
import dev.balakmran.example.api.model.BookResponse;
import dev.balakmran.example.catalog.BookService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** Implements the API generated from openapi/books.yaml. */
@RestController
public class BooksController implements BooksApi {
  private final BookService service;
  private final BookMapper mapper;

  public BooksController(BookService service, BookMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @Override
  public ResponseEntity<List<BookResponse>> listBooks() {
    return ResponseEntity.ok(service.all().stream().map(mapper::toResponse).toList());
  }

  @Override
  public ResponseEntity<BookResponse> addBook(BookRequest request) {
    var book = service.add(request.getIsbn(), request.getTitle(), request.getSubtitle());
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(book));
  }

  @Override
  public ResponseEntity<BookResponse> getBook(String isbn) {
    return ResponseEntity.ok(mapper.toResponse(service.get(isbn)));
  }
}
