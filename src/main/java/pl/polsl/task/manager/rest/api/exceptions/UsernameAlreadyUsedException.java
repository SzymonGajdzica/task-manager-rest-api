package pl.polsl.task.manager.rest.api.exceptions;

public class UsernameAlreadyUsedException extends RuntimeException {

    public UsernameAlreadyUsedException(String userName){
        super("User with name '" + userName + "' already exists");
    }

}
