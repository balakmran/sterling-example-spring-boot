package dev.balakmran.example.catalog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class BookServiceTest {
  private final BookService service = new BookService(new BookRepository());

  @Test
  void addsAndFindsABook() {
    service.add("123", "Dune", null);

    assertThat(service.get("123").getTitle()).isEqualTo("Dune");
    assertThat(service.all()).hasSize(1);
  }

  @Test
  void rejectsABlankTitle() {
    assertThatThrownBy(() -> service.add("123", " ", null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void failsForAnUnknownBook() {
    assertThatThrownBy(() -> service.get("nope")).isInstanceOf(IllegalArgumentException.class);
  }
}
