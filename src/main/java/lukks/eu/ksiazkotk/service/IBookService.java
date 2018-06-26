package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.Book;

public interface IBookService {
    void saveBook(Book book);

    void deleteBook(Long id);

    Book readBook(Long id);
}
