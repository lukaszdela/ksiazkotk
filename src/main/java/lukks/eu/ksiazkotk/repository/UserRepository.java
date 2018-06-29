package lukks.eu.ksiazkotk.repository;

import lukks.eu.ksiazkotk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select e from User e where e.login = :login")
    User getUserByLogin(@Param("login") String login);

    @Query("select e from User e where e.active = 'ACTIVE'")
    List<User> getAllUsers();
}
