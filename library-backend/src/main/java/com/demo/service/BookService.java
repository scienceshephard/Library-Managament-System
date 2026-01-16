package com.demo.service;

import com.demo.exceptions.BookNotFoundException;
import com.demo.model.Book;
import com.demo.repo.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book getBook(Long id) {
        return bookRepo.findById(id).orElseThrow(()-> new BookNotFoundException("Book not found"));
    }
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book existingBook = bookRepo.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
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
