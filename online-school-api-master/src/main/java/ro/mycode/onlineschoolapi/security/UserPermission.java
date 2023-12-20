package ro.mycode.onlineschoolapi.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserPermission {
    BOOK_READ("book:read"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write"),
    BOOK_WRITE("book:write");

    private String permission;
    public String getPermission(){ return permission;}

}
