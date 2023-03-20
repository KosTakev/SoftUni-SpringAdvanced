package com.example.books.service;

import com.example.books.model.dto.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<BookDTO> getBookById(Long bookId);

    List<BookDTO> getAllBooks();

    void deleteBookById(Long bookId);
}
