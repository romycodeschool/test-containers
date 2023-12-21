package ro.mycode.institutie2.Splital.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ro.mycode.institutie2.Splital.system.Constants.NO_UPDATE;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class NoUpdate extends RuntimeException{
    public NoUpdate() {
        super(NO_UPDATE);
    }
}
