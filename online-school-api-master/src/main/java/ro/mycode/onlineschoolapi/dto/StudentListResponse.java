package ro.mycode.onlineschoolapi.dto;

import ro.mycode.onlineschoolapi.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StudentListResponse {
    private String message;
    private List<Student> studentList;


}
