package lukks.eu.ksiazkotk.controller;

import lukks.eu.ksiazkotk.model.Book;
import lukks.eu.ksiazkotk.model.BookStatus;
import lukks.eu.ksiazkotk.model.Status;
import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.service.IBookService;
import lukks.eu.ksiazkotk.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
public class BooksController {

    private IBookService iBookService;
    private IUserService iUserService;

    @Autowired
    public BooksController(IBookService iBookService, IUserService iUserService) {
        this.iBookService = iBookService;
        this.iUserService = iUserService;
    }

    @GetMapping("/")
    public String getBookList(Model model){
        List<Book> books = iBookService.getAllBooks();
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/book/addbook")
    public String addBookForm(){
        return "addbook";
    }

    @GetMapping("/books/all")
    public String getAllBooks(Model model){
        List<Book> books = iBookService.getAllBooks();
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/books/free")
    public String getFreeBooks(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Book> books = iBookService.getAllFreeBook(username);
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/books/borrowed")
    public String getBorrowedBooks(Model model){
        List<Book> books = iBookService.getAllBorrowedBooks();
        model.addAttribute("books", books);
        return "book";
    }
    @RequestMapping(path = "/books/user/{username}", method = RequestMethod.GET)
    public String getUserBooks(@PathVariable("username")String username, Model model){
        List<Book> books = iBookService.getUserBooks(username);
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping(path = "/books/active")
    public String getUserActiveBooks(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Book> books = iBookService.getUserActiveBooks(username);
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping(path = "/books/user")
    public String getUserOwnedBooks(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Book> books = iBookService.getUserBooks(username);
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping(path = "/books")
    public String searchBooks(@RequestParam("search") String search, Model model){
        List<Book> books = iBookService.searchBooks(search);
        model.addAttribute("books", books);
        return "book";
    }

    @PostMapping(value = "/book/addbook/new")
    public String addNewBook(@ModelAttribute Book book){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User owner = iUserService.getUserByLogin(username);
        book.setCover("/covers/default.jpg");
        book.setStatus(BookStatus.FREE);
        book.setActive(Status.ACTIVE);
        book.setOwner(owner);
//        List<Book> userBooks = owner.getBooks();
//        userBooks.add(book);
//        owner.setBooks(userBooks);
//        iUserService.saveUser(owner);
        iBookService.saveBook(book);
        return "redirect:/books/user";
    }

    @RequestMapping(path = "/books/borrow/{bookId}", method = RequestMethod.GET)
    public String borrowBook(@PathVariable("bookId")Long bookId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = iUserService.getUserByLogin(username);
        Book book = iBookService.readBook(bookId);
        book.setStatus(BookStatus.LEND);
        book.setBorower(user);
        iBookService.saveBook(book);
        List<Book> books = iBookService.getUserActiveBooks(username);
        model.addAttribute("books", books);
        return "book";
    }

    @RequestMapping(path = "/books/cancel/{bookId}", method = RequestMethod.GET)
    public String cancelBorrow(@PathVariable("bookId")Long bookId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Book book = iBookService.readBook(bookId);
        book.setStatus(BookStatus.FREE);
        book.setBorower(null);
        iBookService.saveBook(book);
        List<Book> books = iBookService.getUserActiveBooks(username);
        model.addAttribute("books", books);
        return "book";
    }

    @RequestMapping(path = "/books/read/{bookId}", method = RequestMethod.GET)
    public String reciveBook(@PathVariable("bookId")Long bookId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Book book = iBookService.readBook(bookId);
        book.setStatus(BookStatus.READ);
        iBookService.saveBook(book);
        List<Book> books = iBookService.getUserActiveBooks(username);
        model.addAttribute("books", books);
        return "book";
    }

    @RequestMapping(path = "/books/readed/{bookId}", method = RequestMethod.GET)
    public String returnBook(@PathVariable("bookId")Long bookId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Book book = iBookService.readBook(bookId);
        book.setStatus(BookStatus.RETURN);
        iBookService.saveBook(book);
        List<Book> books = iBookService.getUserActiveBooks(username);
        model.addAttribute("books", books);
        return "book";
    }

    @RequestMapping(path = "/books/recive/{bookId}", method = RequestMethod.GET)
    public String getBackBook(@PathVariable("bookId")Long bookId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Book book = iBookService.readBook(bookId);
        book.setStatus(BookStatus.FREE);
        iBookService.saveBook(book);
        List<Book> books = iBookService.getAllBooks();
        model.addAttribute("books", books);
        return "book";
    }

    @RequestMapping(path = "/books/delete/{bookId}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("bookId")Long bookId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Book book = iBookService.readBook(bookId);
        book.setActive(Status.INACTIVE);
        iBookService.saveBook(book);
        List<Book> books = iBookService.getUserBooks(username);
        model.addAttribute("books", books);
        return "book";
    }



}
