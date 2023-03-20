package com.example.books.web;

import com.example.books.model.dto.BookDTO;
import com.example.books.service.BookService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private BookService bookService;

    public BooksController(BookService bookService) {

        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity
                .ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long bookId) {

       Optional<BookDTO> bookOptional = bookService.getBookById(bookId);

       if (bookOptional.isEmpty()) {
           return ResponseEntity
                   .notFound()
                   .build();
       } else {
           return ResponseEntity
                   .ok(bookOptional.get());
       }
    }

    @DeleteMapping("/{id")
    public ResponseEntity<BookDTO> deleteBookById(@Param("id") Long bookId) {

        bookService.deleteBookById(bookId);

        return ResponseEntity.noContent().build();
    }
}
