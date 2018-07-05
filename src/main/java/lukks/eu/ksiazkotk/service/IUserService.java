package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.User;

import java.util.List;

public interface IUserService {
    void saveUser(User user);

    void deleteUser(Long id);

    User readUser(Long id);

    List<User> getAllUsers();

    List<User> getAllUser();

    User getUserByLogin(String login);

//    Optional<User> getUserById(Long id);
}
