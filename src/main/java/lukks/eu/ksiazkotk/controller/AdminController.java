package lukks.eu.ksiazkotk.controller;

import lukks.eu.ksiazkotk.model.Book;
import lukks.eu.ksiazkotk.model.BookStatus;
import lukks.eu.ksiazkotk.model.Status;
import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.service.IBookService;
import lukks.eu.ksiazkotk.service.IUserService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private IUserService iUserService;
    private PasswordEncoder passwordEncoder;
    private IBookService iBookService;

    @Autowired
    public AdminController(IUserService iUserService, PasswordEncoder passwordEncoder, IBookService iBookService) {
        this.iUserService = iUserService;
        this.passwordEncoder = passwordEncoder;
        this.iBookService = iBookService;
    }

    @GetMapping("/admin/books")
    public String getAdminBooks(Model model){
        List<Book> books = iBookService.getAllBook();
        List<User> users = iUserService.getAllUser();
        List<String> statuses =iBookService.findAllStatus();
        List<String> actives = iBookService.findAllActives();
        model.addAttribute("books", books);
        model.addAttribute("users", users);
        model.addAttribute("statuses", statuses);
        model.addAttribute("actives", actives);
        return "adminbook";
    }

    @GetMapping("/admin/users/all")
    public String getAdminUsers(Model model){
        List<User> users = iUserService.getAllUser();
        List<String> actives = iBookService.findAllActives();
        model.addAttribute("users", users);
        model.addAttribute("actives", actives);
        return "adminuser";
    }

    @RequestMapping(path = "/admin/user/update/{userId}", method = RequestMethod.POST)
    public String updateUser(@PathVariable("userId")Long userId,@ModelAttribute User user){
        User userById = iUserService.readUser(userId);
        userById.setEnabled(user.getEnabled());
        userById.setActive(user.getActive());
        userById.setAvatar(user.getAvatar());
        userById.setLogin(user.getLogin());
        userById.setName(user.getName());
        userById.setSurname(user.getSurname());
        userById.setPassword(user.getPassword());

//        if(userById.getActive().equals(Status.INACTIVE)){
//            List<Book> books = iBookService.getUserFreeBooks(userById.getLogin());
//            for (Book book: books) {
//                book.setActive(Status.INACTIVE);
//                iBookService.saveBook(book);
//            }
//        }

        iUserService.saveUser(userById);
        return "redirect:/admin/users/all";
    }

    @RequestMapping(path = "/admin/books/update/{bookId}", method = RequestMethod.POST)
    public String updateUser(@PathVariable("bookId")Long bookId,
                             @ModelAttribute Book book,
                             @RequestParam(required = false) Long ownerId,
                             @RequestParam Long borowerId){
        Book bookById = iBookService.readBook(bookId);
        bookById.setActive(book.getActive());
        bookById.setAuthor(book.getAuthor());
        bookById.setCover(book.getCover());
        bookById.setStatus(book.getStatus());
        bookById.setTitle(book.getTitle());
        iBookService.saveBook(bookById);

        if(!ownerId.equals(bookById.getOwner().getId())){
            User userOld = iUserService.readUser(bookById.getOwner().getId());
            User userNew = iUserService.readUser(ownerId);
            List<Book> booksOldUser = userOld.getBooks();
            List<Book> booksNewUser = userNew.getBooks();
            booksOldUser.remove(bookById);
            booksNewUser.add(bookById);
            userOld.setBooks(booksOldUser);
            userNew.setBooks(booksNewUser);
            bookById.setOwner(userNew);
            iUserService.saveUser(userOld);
            iUserService.saveUser(userNew);
        }

        if(!borowerId.equals(0L)) {
            User borowerById = iUserService.readUser(borowerId);
            bookById.setBorower(borowerById);
        }else {
            bookById.setBorower(null);
        }

        iBookService.saveBook(bookById);
        return "redirect:/admin/books";
    }

    @GetMapping("/admin/users/pass/{userId}")
    public String changeUserPasswordSite(@PathVariable("userId")Long userId, Model model){
        User user = iUserService.readUser(userId);
        model.addAttribute("user", user);
        return "adminpass";
    }

    @PostMapping("/admin/users/pass/change/{userId}")
    public String changeUserPassword(@PathVariable("userId")Long userId,
                                     @RequestParam("password") String password,
                                     @RequestParam("password2") String password2,
                                     Model model){
        User user = iUserService.readUser(userId);
        if(password.equals(password2)){
            user.setPassword(passwordEncoder.encode(password));
            iUserService.saveUser(user);
        }
        model.addAttribute("user", user);
        return "redirect:/admin/users/all";
    }

    @GetMapping(path = "/admin/books/defaultcover")
    public String getDefaultCoverBooks(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Book> books = iBookService.getDefaultCoverBooks();
        model.addAttribute("books", books);
        return "admincover";
    }

    @PostMapping(path = "admin/books/cover/add/{bookId}")
    public String saveBookCover(@PathVariable("bookId") Long bookId, @RequestParam("file") MultipartFile file, Model model){
        iBookService.saveBookCover(bookId,file);
        List<Book> books = iBookService.getAllBooks();
        model.addAttribute("books", books);
        return "redirect:/";
    }

}