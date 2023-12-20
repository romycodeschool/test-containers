package ro.mycode.onlineschoolapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Table(name = "book")
@Entity(name = "Book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "books_sequence"
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @Size(min = 2, message = "min title length should be 2")
    private String title;

    @Size(min = 2, message = "min author length should be 2")
    @Column(name = "author",
            nullable = false)
    private String author;

    @Column(name = "price",
            nullable = false)
    @DecimalMin(value = "0.1", message = "Price should have a value.")
    private double price;

    @Column(name = "stars")
    private Long stars;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_id_fk")

    )
    @JsonBackReference
    private Student student;


    @Override
    public boolean equals(Object o) {
        Book x = (Book) o;
        return x.getTitle().compareTo(this.getTitle()) == 0 && x.getAuthor().compareTo(this.getAuthor()) == 0;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", stars=" + stars +
                '}';
    }
}
