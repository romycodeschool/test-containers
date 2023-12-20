package ro.mycode.onlineschoolapi.dto;

import ro.mycode.onlineschoolapi.security.UserRole;

public record LoginResponse(Long studentId, String email, String token, String firstName, String lastName, UserRole userRole) {
}
