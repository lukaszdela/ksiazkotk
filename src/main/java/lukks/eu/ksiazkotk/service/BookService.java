package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.Book;
import lukks.eu.ksiazkotk.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBookService{

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveBook(Book book){
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book readBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

}