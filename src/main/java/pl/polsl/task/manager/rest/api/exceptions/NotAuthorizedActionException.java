package pl.polsl.task.manager.rest.api.exceptions;

public class NotAuthorizedActionException extends RuntimeException {

    public NotAuthorizedActionException(String message){
        super("Not authorized - " + message);
    }

}
