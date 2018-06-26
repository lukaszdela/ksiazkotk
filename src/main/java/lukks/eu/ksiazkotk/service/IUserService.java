package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.User;

public interface IUserService {
    void saveUser(User user);

    void deleteUser(Long id);

    void readUser(Long id);
}
