package pl.polsl.task.manager.rest.api.exceptions;

public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException(String message){
        super("Forbidden - " + message);
    }

}
