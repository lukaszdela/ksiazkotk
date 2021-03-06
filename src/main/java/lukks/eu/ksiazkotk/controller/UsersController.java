package lukks.eu.ksiazkotk.controller;

import lukks.eu.ksiazkotk.model.BookStatus;
import lukks.eu.ksiazkotk.model.Status;
import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UsersController {

    private IUserService iUserService;
    private PasswordEncoder passwordEncoder;

    public UsersController(IUserService iUserService, PasswordEncoder passwordEncoder) {
        this.iUserService = iUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user")
    public String getAllUsers(Model model){
        List<User> users = iUserService.getAllUsers();
        model.addAttribute("users", users);
        return "user";
    }

    @PostMapping("/user/email")
    public String changeUserEmail(@RequestParam("email") String email, Model model, HttpServletRequest request){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = iUserService.getUserByLogin(username);
            user.setLogin(email);
            iUserService.saveUser(user);
            new SecurityContextLogoutHandler().logout(request, null, authentication);
            String msg = String.format("Your new mail and login is %s", email);
            model.addAttribute("msg", msg);
        }catch (Exception e){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            new SecurityContextLogoutHandler().logout(request, null, authentication);
            String msg = String.format("Entered email adress has already been registered in Ksiazkotk. Your mail has not been changed try another!");
            model.addAttribute("msg", msg);

        }
        return "login";
    }

    @PostMapping("/user/password")
    public String changeUserPassword(@RequestParam("password") String password,
                                     @RequestParam("password2") String password2,
                                     Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = iUserService.getUserByLogin(username);
        if(password.equals(password2)){
        user.setPassword(passwordEncoder.encode(password));
        iUserService.saveUser(user);
            String msg = String.format("Your password has been changed.");
            model.addAttribute("msg", msg);
        }else{
            String msg = String.format("Entered passwords do not match. Please try again.");
            model.addAttribute("msg", msg);
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/user/profile")
    public String getUserProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = iUserService.getUserByLogin(username);
        model.addAttribute("user", user);
        return "profile";
    }


}
