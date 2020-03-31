package pl.polsl.task.manager.rest.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {

    public NotFoundException(String code) {
        super("Could not found any match for code '" + code + "'");
    }

    public NotFoundException(Long id) {
        super("Could not found any match for id '" + id + "'");
    }

}
