package ro.mycode.onlineschoolapi.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Min;

@Table(name="request")
@Entity(name="Request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Request {
    @Id
    @SequenceGenerator(
            name = "request_sequence",
            sequenceName = "request_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "request_sequence")
    Long id;

    @Column(name = "studentId",
            nullable = false)
    Long studentId;

    @Column(name = "courseId",
            nullable = false)
    Long courseId;

    @Column(name="satus")
    Boolean status;
    public Request(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
