package ro.mycode.onlineschoolapi.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.repository.StudentRepo;

@Component
public class UserDetailsImpl implements UserDetailsService {
    private StudentRepo studentRepo;

    public UserDetailsImpl(StudentRepo studentRepo) {
       this.studentRepo=studentRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Student user=studentRepo.findStudentsByEmail(s).get();
        if(user!=null){
            return user;
        }

        throw new UsernameNotFoundException(
                "User " +s+" not found"
        );
    }
}
