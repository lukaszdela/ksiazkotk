package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.Book;
import lukks.eu.ksiazkotk.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BookServiceTest {

    private IBookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ImageFileService imageFileService;

    @Before
    public void setUp(){
        initMocks(this);
        bookService = new BookService(bookRepository,imageFileService);
    }

    @Test
    public void shouldSaveBook(){

        Book book = new Book();

        Mockito.when(bookRepository.save(book)).thenReturn(book);

        bookService.saveBook(book);

        Mockito.verify(bookRepository,Mockito.times(1)).save(book);
    }

}