package lukks.eu.ksiazkotk.controller;

import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.service.IUserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UsersControllerTest {

    private UsersController usersController;

    @Mock
    private IUserService iUserService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp(){
        initMocks(this);
        usersController = new UsersController(iUserService,passwordEncoder);
    }


    @Test
    public void shouldReturnAllUsers(){

        List<User> users = Arrays.asList(new User(),new User());


        Mockito.when(iUserService.getAllUsers()).thenReturn(users);

        Model model = Mockito.mock(Model.class);
        Mockito.when(model.addAttribute("users",users)).thenReturn(model);

        String website = usersController.getAllUsers(model);

        Assert.assertEquals("user",website);
        Mockito.verify(model,Mockito.times(1)).addAttribute("users",users);
        Mockito.verify(iUserService,Mockito.times(1)).getAllUsers();
    }
}