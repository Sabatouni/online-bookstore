package com.Arif00.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Arif00.bookstore.model.Book;
import com.Arif00.bookstore.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    // Constructor injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll(); // Retrieve all books from the database
    }

    // Get a book by its ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id); // Retrieve a book by its ID
    }

    // Add a new book
    public void addBook(Book book) {
        bookRepository.save(book); // Save the book to the database
    }

    // Update an existing book
    public void updateBook(Book book) {
        bookRepository.update(book); // Update the book in the database
    }

    // Delete a book by its ID
    public void deleteBook(Long id) {
        bookRepository.deleteById(id); // Delete the book from the database
    }
}
