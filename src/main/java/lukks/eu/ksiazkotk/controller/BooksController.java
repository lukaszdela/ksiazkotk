package lukks.eu.ksiazkotk.controller;

import lukks.eu.ksiazkotk.model.Book;
import lukks.eu.ksiazkotk.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BooksController {

    private IBookService iBookService;

    @Autowired
    public BooksController(IBookService iBookService) {
        this.iBookService = iBookService;
    }

    @GetMapping("/")
    public String getBookList(Model model){
        List<Book> books = iBookService.getAllBook();
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/books/all")
    public String getAllBooks(Model model){
        List<Book> books = iBookService.getAllBook();
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

//    @GetMapping("/books/search")
//    public String searchBooks(@RequestParam()String search){
//
//    }

}
