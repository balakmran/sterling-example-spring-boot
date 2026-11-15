package dev.balakmran.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class BooksApiIT {
  @Autowired WebApplicationContext context;

  @Test
  void addsABookAndReadsItBack() throws Exception {
    MockMvc mvc = MockMvcBuilders.webAppContextSetup(context).build();

    mvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"isbn\":\"9780441172719\",\"title\":\"Dune\"}"))
        .andExpect(status().isCreated());
    mvc.perform(get("/books/9780441172719"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Dune"));
    mvc.perform(get("/books")).andExpect(jsonPath("$[0].isbn").value("9780441172719"));
  }
}
