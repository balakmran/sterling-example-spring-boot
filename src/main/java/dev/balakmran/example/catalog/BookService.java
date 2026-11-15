package dev.balakmran.example.catalog;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/** The catalog's use cases. */
@Service
public class BookService {
  private final BookRepository repository;

  public BookService(BookRepository repository) {
    this.repository = repository;
  }

  /** Adds a book; its ISBN and title must be present. */
  public Book add(String isbn, String title, @org.jspecify.annotations.Nullable String subtitle) {
    Assert.hasText(isbn, "isbn must not be blank");
    Assert.hasText(title, "title must not be blank");
    return repository.save(Book.builder().isbn(isbn).title(title).subtitle(subtitle).build());
  }

  /** Finds a book by ISBN, or throws when there is none. */
  public Book get(String isbn) {
    Book book = repository.findByIsbn(isbn).orElse(null);
    // Spring's Assert.notNull carries a @Contract, so NullAway sees book as non-null below.
    Assert.notNull(book, () -> "no book with isbn " + isbn);
    return book;
  }

  public List<Book> all() {
    return repository.findAll();
  }
}
