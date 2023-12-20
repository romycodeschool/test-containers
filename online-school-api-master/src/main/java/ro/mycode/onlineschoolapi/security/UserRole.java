package ro.mycode.onlineschoolapi.security;


import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ro.mycode.onlineschoolapi.security.UserPermission.*;


@AllArgsConstructor
public enum UserRole {
    STUDENT(Sets.newHashSet(BOOK_READ,BOOK_WRITE,COURSE_READ)),
    ADMIN(Sets.newHashSet(BOOK_READ,BOOK_WRITE,COURSE_READ,COURSE_WRITE));

    private final Set<UserPermission> permissions;
    public Set<UserPermission> getPermissions(){return  permissions;}

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> collect=getPermissions()
                .stream()
                .map(e->new SimpleGrantedAuthority(e.getPermission()))
                .collect(Collectors.toSet());

        collect.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return collect;
    }
}
