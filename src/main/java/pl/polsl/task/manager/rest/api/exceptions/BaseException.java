package pl.polsl.task.manager.rest.api.exceptions;

public abstract class BaseException extends RuntimeException {

    protected BaseException(String message) {
        super(message);
    }

}
