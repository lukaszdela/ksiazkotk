package lukks.eu.ksiazkotk.model;

import lombok.*;

import javax.persistence.*;

@Builder
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
    private String cover;
    private Status active;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id")
    private User borower;

    @Enumerated(EnumType.STRING)
    private BookStatus status;
}
