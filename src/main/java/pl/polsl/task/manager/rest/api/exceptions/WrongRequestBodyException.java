package pl.polsl.task.manager.rest.api.exceptions;

public class WrongRequestBodyException extends RuntimeException {

    public WrongRequestBodyException(String message) {
        super("Wrong request body - " + message);
    }

}
