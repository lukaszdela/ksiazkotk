package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBookService {
    void saveBook(Book book);

    void deleteBook(Long id);

    Book readBook(Long id);

    List<Book> getAllFreeBook(String login);

    List<Book> getAllBorrowedBooks();

    List<Book> getAllBook();

    List<Book> getUserFreeBooksInactive(String login);

    List<Book> getUserFreeBooks(String login);

    List<Book> getUserBooks(String login);

    List<Book> getUserActiveBooks(String login);

    List<Book> searchBooks(String search);

    List<Book> getAllBooks();

    List<Book> getDefaultCoverBooks();

    void saveBookCover(Long bookId, MultipartFile file);

    List<String> findAllStatus();

    List<String> findAllActives();
}
