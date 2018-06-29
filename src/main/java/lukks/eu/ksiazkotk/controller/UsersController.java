package lukks.eu.ksiazkotk.controller;

import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UsersController {

    private IUserService iUserService;

    @Autowired
    public UsersController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("/user")
    public String getAllUsers(Model model){
        List<User> users = iUserService.getAllUsers();
        model.addAttribute("users", users);
        return "user";
    }

    @PostMapping("/user/email")
    public String changeUserEmail(@RequestParam("email") String email, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = iUserService.getUserByLogin(username);
        user.setEmail(email);
        iUserService.saveUser(user);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/user/password")
    public String changeUserPassword(@RequestParam("password") String password,
                                     @RequestParam("password2") String password2,
                                     Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = iUserService.getUserByLogin(username);
        if(password.equals(password2)){
        user.setPassword(password);
        iUserService.saveUser(user);
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
