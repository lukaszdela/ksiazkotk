package lukks.eu.ksiazkotk.controller;

import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UsersController {

    private IUserService iUserService;

    @Autowired
    public UsersController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<User> users = iUserService.getAllUsers();
        model.addAttribute("users", users);
        return "user";
    }
}
