package ro.mycode.onlineschoolapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class EnrollRequestStudentToCourse {

    @NotNull
    private Long idStudent;

    @NotNull
    private Long idCourse;

    private Boolean status;

}
