package com.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.dto.PageResponse;
import com.demo.exceptions.BookNotFoundException;
import com.demo.model.Book;
import com.demo.repo.BookRepo;

@Service
public class BookService {
    private BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public PageResponse<Book> getAllBooksPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepo.findAll(pageable);
        return new PageResponse<>(
                bookPage.getContent(),
                bookPage.getNumber(),
                bookPage.getSize(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages()
        );
    }

    public PageResponse<Book> searchBooks(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                query, query, pageable);
        return new PageResponse<>(
                bookPage.getContent(),
                bookPage.getNumber(),
                bookPage.getSize(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages()
        );
    }

    public Book getBook(Long id) {
        return bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book existingBook = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPublishedDate(book.getPublishedDate());
        return bookRepo.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }
}

