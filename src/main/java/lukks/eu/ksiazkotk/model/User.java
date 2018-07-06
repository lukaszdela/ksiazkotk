package lukks.eu.ksiazkotk.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
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
    private String password;
    private String avatar;
    private Status active;
    private Boolean enabled;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @OneToMany(mappedBy = "borower")
    private List<Book> borrowerBooks;

    @OneToMany(fetch = FetchType.EAGER)
    private List<UserRole> userRoles;


}
