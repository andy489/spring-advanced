package bg.softuni.bookserver.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Table(name = "books")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;

    @ManyToOne
    @ToString.Exclude
    private AuthorEntity author;

    @ToString.Include(name = "authorName")
    public String getAuthorName() {
        return author != null ? author.getName() : null;
    }
}
