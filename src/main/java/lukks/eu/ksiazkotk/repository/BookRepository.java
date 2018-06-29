package lukks.eu.ksiazkotk.repository;

import lukks.eu.ksiazkotk.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select e from Book e where e.active = 'ACTIVE'")
    List<Book> getAllBooks();

    @Query("select e from Book e inner join e.owner ow where ow.login <> :login AND e.status = 'FREE' and e.active = 'ACTIVE'")
    List<Book> getAllFreeBooks(@Param("login") String login);

    @Query("select e from Book e where e.status <> 'FREE' and e.active = 'ACTIVE'")
    List<Book> getAllBorrowedBooks();

    @Query("select e from Book e inner join e.owner ow where ow.login = :login and e.active = 'ACTIVE'")
    List<Book> getUserBooks(@Param("login") String login);

    @Query("select e from Book e inner join e.borower ow where ow.login = :login and e.active = 'ACTIVE'")
    List<Book> getUserActiveBooks(@Param("login") String login);

    @Query("select e from Book e where e.title like %:search% or e.author like %:search% and e.active = 'ACTIVE'")
    List<Book> searchBooks(@Param("search") String search);

}
