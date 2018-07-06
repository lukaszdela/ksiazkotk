package lukks.eu.ksiazkotk.controller;

import lukks.eu.ksiazkotk.model.Status;
import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.model.UserRole;
import lukks.eu.ksiazkotk.service.IUserRoleService;
import lukks.eu.ksiazkotk.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RegisterController {

    private IUserService iUserService;
    private IUserRoleService iUserRoleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(IUserService iUserService, IUserRoleService iUserRoleService, PasswordEncoder passwordEncoder) {
        this.iUserService = iUserService;
        this.iUserRoleService = iUserRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerNewUserForm(){
        return "register";
    }

    @PostMapping(value = "/register/new")
    public String addNewBook(@ModelAttribute User user,
                             @RequestParam("pass") String pass,
                             @RequestParam("pass2") String pass2){
        user.setActive(Status.NEW);
        user.setEnabled(Boolean.FALSE);
        if(pass.equals(pass2)){
            user.setPassword(passwordEncoder.encode(pass));
            UserRole userRole = UserRole.builder().user(user).role("ROLE_USER").build();
            iUserRoleService.saveUser(userRole);
            List<UserRole> userRoleList = new ArrayList<>();
            userRoleList.add(userRole);
            user.setUserRoles(userRoleList);
            iUserService.saveUser(user);
        }
        return "redirect:/login";
    }
}
