package ro.mycode.onlineschoolapi.dto;

import ro.mycode.onlineschoolapi.security.UserRole;

public record StudentRequest(Long studentId,String firstName, String secondName, String email, double age, String password, UserRole role) {
}
