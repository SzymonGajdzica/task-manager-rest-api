package pl.polsl.task.manager.rest.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenAccessException extends BaseException {

    public ForbiddenAccessException(String message) {
        super(message);
    }

    public ForbiddenAccessException(Class... users) {
        super(produceMessage(users));
    }

    private static String produceMessage(Class... users) {
        List<String> classNames = Arrays
                .stream(users)
                .map(Class::getSimpleName)
                .collect(Collectors.toList());
        return "This resource or request is available only for: " + String.join(" , ", classNames);
    }

}
