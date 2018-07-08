package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.model.UserRole;

public interface IUserRoleService {
    void saveUser(UserRole user);


    void deleteByUserId(Long id);

    UserRole getUserRoleByUserId(Long id);
}
