package com.example.books.service.impl;

import com.example.books.model.dto.AuthorDTO;
import com.example.books.model.dto.BookDTO;
import com.example.books.model.entity.BookEntity;
import com.example.books.repository.BookRepository;
import com.example.books.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {


    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<BookDTO> getBookById(Long bookId) {
        return bookRepository
                .findById(bookId)
                .map(this::map);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    private BookDTO map(BookEntity bookEntity) {
        return new BookDTO()
                .setId(bookEntity.getId())
                .setTitle(bookEntity.getTitle())
                .setIsbn(bookEntity.getIsbn())
                .setAuthor(new AuthorDTO().setName(bookEntity.getAuthor().getName()));
    }
}
