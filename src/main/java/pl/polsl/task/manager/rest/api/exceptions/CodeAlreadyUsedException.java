package pl.polsl.task.manager.rest.api.exceptions;

public class CodeAlreadyUsedException extends RuntimeException {

    public CodeAlreadyUsedException(String code){
        super("Code '" + code + "' already used");
    }

}
