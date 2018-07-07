package lukks.eu.ksiazkotk.controller;

import lukks.eu.ksiazkotk.model.Status;
import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.model.UserRole;
import lukks.eu.ksiazkotk.service.IUserRoleService;
import lukks.eu.ksiazkotk.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
                             @RequestParam("pass2") String pass2,
                             Model model){
      try{
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
              String msg = String.format("Congratulations You are registered user of Ksiazkoteka, best" +
                      " book swap with friends service ever!!! Wait for Admin to activate Your account." +
                      " Your login is %s",user.getLogin());
              model.addAttribute("msg", msg);
          }else {
              String msg = String.format("Fill registration form correctly and enjoy power of Ksiazkoteka!");
              model.addAttribute("msg", msg);
          }
      }catch (Exception e){
          String msg = String.format("Entered email adress has already been registered in Ksiazkotk, try another!");
          model.addAttribute("msg", msg);

      }

        return "login";
    }
}
