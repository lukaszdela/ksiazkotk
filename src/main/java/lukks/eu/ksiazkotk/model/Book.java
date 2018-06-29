package lukks.eu.ksiazkotk.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String cover;
    private Status active;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "books")
    private User owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borower")
    private User borower;

    @Enumerated(EnumType.STRING)
    private BookStatus status;
}
