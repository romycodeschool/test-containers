package ro.mycode.institutie2.Splital.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ro.mycode.institutie2.Splital.system.Constants.SPITAL_LIST_EMPTY;
@ResponseStatus(HttpStatus.NO_CONTENT)
public class SpitalListEmpty extends RuntimeException{
    public SpitalListEmpty() {
        super(SPITAL_LIST_EMPTY);
    }
}
