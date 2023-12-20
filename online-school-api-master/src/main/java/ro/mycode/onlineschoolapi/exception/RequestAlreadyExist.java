package ro.mycode.onlineschoolapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestAlreadyExist extends  RuntimeException{
    public RequestAlreadyExist(String message) {
        super(message);
    }
}
