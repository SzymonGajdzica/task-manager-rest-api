package pl.polsl.task.manager.rest.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UsernameAlreadyUsedException extends BaseException {

    public UsernameAlreadyUsedException(String userName) {
        super("User with username '" + userName + "' already exists");
    }

}
