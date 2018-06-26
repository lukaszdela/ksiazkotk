package lukks.eu.ksiazkotk.repository;

import lukks.eu.ksiazkotk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
