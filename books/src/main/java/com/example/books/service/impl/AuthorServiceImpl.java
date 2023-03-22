package com.example.books.service.impl;

import com.example.books.model.entity.AuthorEntity;
import com.example.books.repository.AuthorRepository;
import com.example.books.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<AuthorEntity> findAuthorByName(String authorName) {
        return authorRepository.findByName(authorName);
    }

    @Override
    public AuthorEntity save(AuthorEntity newAuthor) {
        return authorRepository.save(newAuthor);
    }


}
