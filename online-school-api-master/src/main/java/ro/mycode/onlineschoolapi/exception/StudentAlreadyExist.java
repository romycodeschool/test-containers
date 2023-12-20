package ro.mycode.onlineschoolapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentAlreadyExist extends RuntimeException{

    public StudentAlreadyExist(String message) {
        super(message);
    }
}
