package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.User;

import java.util.List;

public interface IUserService {
    void saveUser(User user);

    void deleteUser(Long id);

    void readUser(Long id);

    List<User> getAllUsers();

    User getUserByLogin(String login);
}
