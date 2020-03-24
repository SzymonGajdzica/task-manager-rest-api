package pl.polsl.task.manager.rest.api.exceptions;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(String message){
        super(message);
    }

}
