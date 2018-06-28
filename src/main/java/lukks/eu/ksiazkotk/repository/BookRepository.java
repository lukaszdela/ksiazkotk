package lukks.eu.ksiazkotk.repository;

import lukks.eu.ksiazkotk.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select e from Book e inner join e.owner ow where ow.login <> :login AND e.status = 'FREE'")
    List<Book> getAllFreeBooks(@Param("login") String login);

    @Query("select e from Book e where e.status <> 'FREE'")
    List<Book> getAllBorrowedBooks();

    @Query("select e from Book e inner join e.owner ow where ow.login = :login")
    List<Book> getUserBooks(@Param("login") String login);

}
