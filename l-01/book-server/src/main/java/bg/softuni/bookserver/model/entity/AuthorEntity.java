package bg.softuni.bookserver.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="authors")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<BookEntity> books = new ArrayList<>();;

    @ToString.Include(name = "bookCount")
    public int getBookCount() {
        return books != null ? books.size() : 0;
    }
}
