package lukks.eu.ksiazkotk.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String login;
    private String email;
    private String password;
    private String avatar;
    private Status active;

    @OneToMany
    private List<Book> books;

    @OneToMany(fetch = FetchType.EAGER)
    private List<UserRole> userRoles;


}
