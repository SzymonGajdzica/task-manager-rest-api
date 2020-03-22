package pl.polsl.task.manager.rest.api.exceptions;

public class WrongRequestException extends RuntimeException {

    public WrongRequestException(String message){
        super(message);
    }

}
