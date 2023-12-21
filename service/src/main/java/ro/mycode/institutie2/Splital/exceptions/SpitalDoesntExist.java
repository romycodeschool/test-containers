package ro.mycode.institutie2.Splital.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ro.mycode.institutie2.Splital.system.Constants.SPITAL_DOESNT_EXIST;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SpitalDoesntExist extends RuntimeException{
    public SpitalDoesntExist() {
        super(SPITAL_DOESNT_EXIST);
    }
}
