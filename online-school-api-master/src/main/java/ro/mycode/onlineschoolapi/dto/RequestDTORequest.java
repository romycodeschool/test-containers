package ro.mycode.onlineschoolapi.dto;

public record RequestDTORequest(Long id,Long studentId,Long courseId,Boolean status,String studentEmail,String courseName) {
}
