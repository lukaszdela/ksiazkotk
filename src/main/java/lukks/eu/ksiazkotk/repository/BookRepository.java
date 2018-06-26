package lukks.eu.ksiazkotk.repository;

import lukks.eu.ksiazkotk.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
