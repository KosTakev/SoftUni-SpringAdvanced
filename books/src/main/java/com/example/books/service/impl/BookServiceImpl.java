package com.example.books.service.impl;

import com.example.books.model.dto.AuthorDTO;
import com.example.books.model.dto.BookDTO;
import com.example.books.model.entity.AuthorEntity;
import com.example.books.model.entity.BookEntity;
import com.example.books.repository.BookRepository;
import com.example.books.service.AuthorService;
import com.example.books.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {


    private BookRepository bookRepository;
    private AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
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

    @Override
    public Long createBook(BookDTO newBook) {
        String authorName = newBook.getAuthor().getName();
        Optional<AuthorEntity> authorOpt = authorService.findAuthorByName(authorName);

        BookEntity entityToBeSaved = new BookEntity()
                .setTitle(newBook.getTitle())
                .setIsbn(newBook.getIsbn())
                .setAuthor(authorOpt.isPresent()
                ? authorOpt.get()
                        :authorService.save(new AuthorEntity().setName(authorName)));
        return entityToBeSaved.getId();
    }

    private BookDTO map(BookEntity bookEntity) {
        return new BookDTO()
                .setId(bookEntity.getId())
                .setTitle(bookEntity.getTitle())
                .setIsbn(bookEntity.getIsbn())
                .setAuthor(new AuthorDTO().setName(bookEntity.getAuthor().getName()));
    }
}
