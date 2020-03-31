package pl.polsl.task.manager.rest.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedException extends BaseException {

    public NotImplementedException(String message) {
        super(message);
    }

}
