package lukks.eu.ksiazkotk.service;

import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.model.UserRole;
import lukks.eu.ksiazkotk.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService implements IUserRoleService{

    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void saveUser(UserRole user){
        userRoleRepository.save(user);
    }
}
