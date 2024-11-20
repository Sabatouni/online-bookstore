package com.Arif00.bookstore.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.Arif00.bookstore.model.Book;

@Repository
public class BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Fetch all books from the database
    public List<Book> findAll() {
        String sql = "SELECT b.book_id AS id, b.title, a.name AS author, p.name AS provider, b.price, b.published_date "
                +
                "FROM Books b " +
                "JOIN Authors a ON b.author_id = a.author_id " +
                "JOIN Providers p ON b.provider_id = p.provider_id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
    }

    // Find a book by its ID
    public Book findById(Long id) {
        String sql = "SELECT b.book_id AS id, b.title, a.name AS author, p.name AS provider, b.price, b.published_date "
                +
                "FROM Books b " +
                "JOIN Authors a ON b.author_id = a.author_id " +
                "JOIN Providers p ON b.provider_id = p.provider_id " +
                "WHERE b.book_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), id);
        } catch (Exception e) {
            return null; // Return null if the book is not found
        }
    }

    // Save a new book to the database
    public void save(Book book) {
        String sql = "INSERT INTO Books (title, author_id, provider_id, price, published_date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getProvider(), book.getPrice(),
                book.getPublishedDate());
    }

    // Update an existing book in the database
    public void update(Book book) {
        String sql = "UPDATE Books SET title = ?, author_id = ?, provider_id = ?, price = ?, published_date = ? WHERE book_id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getProvider(), book.getPrice(),
                book.getPublishedDate(), book.getId());
    }

    // Delete a book by its ID
    public void deleteById(Long id) {
        String sql = "DELETE FROM Books WHERE book_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
