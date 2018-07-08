package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user){
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }


    @Override
    public User readUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    @Override
    public List<User> getAllUsersForAdmin(){
        return userRepository.getAllUsersForAdmin();
    }

    @Override
    public User getUserByLogin(String login){
        return userRepository.getUserByLogin(login);
    }

    @Override
    public void deleteUserById(Long id){
       userRepository.deleteById(id);
    }

}
