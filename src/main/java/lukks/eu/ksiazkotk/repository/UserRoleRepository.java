package lukks.eu.ksiazkotk.repository;

import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("select e from UserRole e inner join e.user us where us.id like :id")
    UserRole getRoleByUserId(@Param("id") Long id);

    void deleteById(Long id);
}
