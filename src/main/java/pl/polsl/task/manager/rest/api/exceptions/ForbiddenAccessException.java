package pl.polsl.task.manager.rest.api.exceptions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException(String message) {
        super(message);
    }

    public ForbiddenAccessException(Class... users) {
        super(produceMessage(users));
    }

    private static String produceMessage(Class...users) {
        List<String> classNames = Arrays
                .stream(users)
                .map(Class::getSimpleName)
                .collect(Collectors.toList());
        return "This resource is available only for: " + String.join(" , ", classNames);
    }

}
