package ro.mycode.onlineschoolapi.dto;


import ro.mycode.onlineschoolapi.security.UserRole;

public record StudentDTO(String firstName, String secondName, String email, double age, String password, String role) {
}
