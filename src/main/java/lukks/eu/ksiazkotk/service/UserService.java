package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.Book;
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
    public void readUser(Long id){
        userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserByLogin(String login){
        return userRepository.getUserByLogin(login);
    }

}
