package lukks.eu.ksiazkotk.controller;

import lukks.eu.ksiazkotk.model.*;
import lukks.eu.ksiazkotk.service.IBookService;
import lukks.eu.ksiazkotk.service.IUserRoleService;
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
    private IUserRoleService iUserRoleService;

    @Autowired
    public AdminController(IUserService iUserService, PasswordEncoder passwordEncoder, IBookService iBookService, IUserRoleService iUserRoleService) {
        this.iUserService = iUserService;
        this.passwordEncoder = passwordEncoder;
        this.iBookService = iBookService;
        this.iUserRoleService = iUserRoleService;
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

        if(userById.getActive().equals(Status.INACTIVE)){
            List<Book> books = iBookService.getUserFreeBooks(userById.getLogin());
            for (Book book: books) {
                book.setActive(Status.INACTIVE);
                iBookService.saveBook(book);
            }
        }

        if(userById.getActive().equals(Status.ACTIVE)){
            List<Book> books = iBookService.getUserFreeBooksInactive(userById.getLogin());
            for (Book book: books) {
                book.setActive(Status.ACTIVE);
                iBookService.saveBook(book);
            }
        }

        iUserService.saveUser(userById);
        return "redirect:/admin/users/all";
    }

    @RequestMapping(path = "/admin/books/cover/{bookId}", method = RequestMethod.POST)
    public String updateDefaultCover(@PathVariable("bookId")Long bookId,
                                     @RequestParam String cover){
        Book bookById = iBookService.readBook(bookId);
        bookById.setCover(cover);
        iBookService.saveBook(bookById);
        return "redirect:/admin/books";
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
            String msg = String.format("Password has been changed.");
            model.addAttribute("msg", msg);
        }else{
            String msg = String.format("Entered passwords do not match. Please try again.");
            model.addAttribute("msg", msg);
        }
        model.addAttribute("user", user);
        return "adminpass";
    }

    @GetMapping(path = "/admin/books/defaultcover")
    public String getDefaultCoverBooks(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Book> books = iBookService.getDefaultCoverBooks();
        model.addAttribute("books", books);
        return "admincover";
    }

    @GetMapping(path = "/admin/users/delete/{userId}")
    public String deleteUserById(@PathVariable("userId")Long userId){
        User user = iUserService.readUser(userId);

        if (!user.getLogin().equals("admin") || !user.getLogin().equals("deleteduser")) {
            List<Book> books = new ArrayList<>();
            List<Book> borrowedBooks = new ArrayList<>();
            borrowedBooks.addAll(user.getBorrowerBooks());
            books.addAll(user.getBooks());
            user.setBooks(null);
            user.setUserRoles(null);
            user.setBorrowerBooks(null);
            User deletedUser = iUserService.getUserByLogin("deleteduser");
            for (Book book: borrowedBooks){
                book.setBorower(deletedUser);
                iBookService.saveBook(book);
            }
            for(Book book:books){
                if(book.getStatus().equals(BookStatus.FREE)){
                    book.setActive(Status.DELETED);
                }
                book.setOwner(deletedUser);
                iBookService.saveBook(book);
            }
            deletedUser.setBooks(books);
            iUserService.saveUser(user);
            iUserService.saveUser(deletedUser);
            UserRole userRole = iUserRoleService.getUserRoleByUserId(userId);
            iUserRoleService.deleteByUserId(userRole.getId());
        }
        return "redirect:/admin/users/all";
    }

    @GetMapping(path = "/admin/books/delete/{bookId}")
    public String deleteBookById(@PathVariable("bookId")Long bookId){
        Book book = iBookService.readBook(bookId);
        if (book.getOwner().getLogin().equals("deleteduser") && book.getActive().equals(Status.DELETED)){
            User owner = iUserService.readUser(book.getOwner().getId());
            List<Book> ownerBooks = owner.getBooks();
            ownerBooks.remove(book);
            List<Book> newOwnerBooks = new ArrayList<>();
            newOwnerBooks.addAll(ownerBooks);
            iUserService.saveUser(owner);
            iBookService.deleteBook(bookId);
        }
        return "redirect:/admin/books";
    }
//    @PostMapping(path = "admin/books/cover/add/{bookId}")
//    public String saveBookCover(@PathVariable("bookId") Long bookId, @RequestParam("file") MultipartFile file, Model model){
//        iBookService.saveBookCover(bookId,file);
//        List<Book> books = iBookService.getAllBooks();
//        model.addAttribute("books", books);
//        return "redirect:/";
//    }

}
