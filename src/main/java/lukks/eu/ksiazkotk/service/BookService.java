package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.Book;
import lukks.eu.ksiazkotk.model.BookStatus;
import lukks.eu.ksiazkotk.model.Status;
import lukks.eu.ksiazkotk.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService{

    private BookRepository bookRepository;

    private ImageFileService imageFileService;

    @Autowired
    public BookService(BookRepository bookRepository,ImageFileService imageFileService) {
        this.bookRepository = bookRepository;
        this.imageFileService = imageFileService;
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

    @Override
    public List<Book> getAllFreeBook(String login){
        return bookRepository.getAllFreeBooks(login);
    }

    @Override
    public List<Book> getAllBorrowedBooks(){
        return bookRepository.getAllBorrowedBooks();
    }

    @Override
    public  List<Book> getAllBook(){
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getUserFreeBooksInactive(String login){
        return bookRepository.getUserFreeBooksInactive(login);
    }

    @Override
    public List<Book> getUserFreeBooks(String login){
        return bookRepository.getUserFreeBooks(login);
    }

    @Override
    public List<Book> getUserBooks(String login){
        return bookRepository.getUserBooks('%' + login + '%');
    }

    @Override
    public List<Book> getUserActiveBooks(String login){
        return bookRepository.getUserActiveBooks(login);
    }

    @Override
    public List<Book> searchBooks(String search){
        return bookRepository.searchBooks(search);
    }

    @Override
    public List<Book> getAllBooks(){
        return bookRepository.getAllBooks();
    }

    @Override
    public List<Book> getDefaultCoverBooks(){
        return bookRepository.getDefaultCoverBooks();
    }

    @Override
    public void saveBookCover(Long bookId, MultipartFile file) {
        String coverPath = imageFileService.save(file);
        Book book = readBook(bookId);
        book.setCover(coverPath);
        bookRepository.save(book);
    }

    @Override
    public List<String> findAllStatus() {
        return Arrays.stream(BookStatus.values())
                .map(BookStatus::name).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllActives() {
        return Arrays.stream(Status.values())
                .map(Status::name).collect(Collectors.toList());
    }
}
