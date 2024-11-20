package com.Arif00.bookstore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Arif00.bookstore.model.Book;
import com.Arif00.bookstore.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    // Constructor injection for BookService
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    private static final String REDIRECT_BOOKS = "redirect:/books";

    // Method to show the list of books
    @GetMapping
    public String showBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book_list"; // Thymeleaf template for displaying the list of books
    }

    // Method to show the add book form
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book()); // Empty book object for the form
        return "add_book"; // Thymeleaf template for adding a book
    }

    // Method to handle the form submission for creating a new book
    @PostMapping
    public String addBook(@ModelAttribute Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            return "add_book"; // Simple validation: if title is empty, return the form
        }
        bookService.addBook(book); // Save the book using the service
        return REDIRECT_BOOKS; // Redirect to the list of books
    }

    // Method to show the edit book form (for updating)
    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return REDIRECT_BOOKS; // Redirect to books list if the book is not found
        }
        model.addAttribute("book", book);
        return "edit_book"; // Thymeleaf template for editing a book
    }

    // Method to handle the form submission for updating a book
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            return "edit_book"; // Simple validation: if title is empty, return the form
        }
        book.setId(id); // Set the ID for the book to update the existing record
        bookService.updateBook(book); // Update the book using the service
        return REDIRECT_BOOKS; // Redirect to the list of books
    }

    // Method to handle the deletion of a book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id); // Call the service to delete the book
        return REDIRECT_BOOKS; // Redirect back to the list of books
    }
}
