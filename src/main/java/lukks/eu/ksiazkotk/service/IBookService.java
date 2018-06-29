package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.Book;

import java.util.List;

public interface IBookService {
    void saveBook(Book book);

    void deleteBook(Long id);

    Book readBook(Long id);

    List<Book> getAllFreeBook(String login);

    List<Book> getAllBorrowedBooks();

    List<Book> getAllBook();

    List<Book> getUserBooks(String login);

    List<Book> getUserActiveBooks(String login);

    List<Book> searchBooks(String search);

    List<Book> getAllBooks();
}
