package ro.mycode.onlineschoolapi.dto;

import ro.mycode.onlineschoolapi.security.UserRole;

public record RegisterResponse(Long studentId, String firstName, String secondName, String email , double age , String token) {
}
