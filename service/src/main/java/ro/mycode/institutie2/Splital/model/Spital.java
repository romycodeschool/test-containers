package ro.mycode.institutie2.Splital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="splitale")
@Data
@AllArgsConstructor
@SuperBuilder
@ToString
@NoArgsConstructor
public class Spital implements Comparable<Spital>{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private String denumire;
    private String adresa;
    private String specializare;

    @Override
    public int compareTo(Spital o) {
        return 0;
    }
}
