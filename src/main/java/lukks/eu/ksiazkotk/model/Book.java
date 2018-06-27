package lukks.eu.ksiazkotk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borower")
    private User borower;

    @Enumerated(EnumType.STRING)
    private BookStatus status;
}
