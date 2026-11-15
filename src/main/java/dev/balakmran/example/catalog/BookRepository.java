package dev.balakmran.example.catalog;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

/** Keeps books in memory, so the example needs no database. */
@Repository
public class BookRepository {
  private final Map<String, Book> books = new ConcurrentHashMap<>();

  public Book save(Book book) {
    books.put(book.getIsbn(), book);
    return book;
  }

  public Optional<Book> findByIsbn(String isbn) {
    return Optional.ofNullable(books.get(isbn));
  }

  public List<Book> findAll() {
    return List.copyOf(books.values());
  }
}
