package pl.polsl.task.manager.rest.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends BaseException {

    public NotAuthorizedException(String message) {
        super(message);
    }

}
