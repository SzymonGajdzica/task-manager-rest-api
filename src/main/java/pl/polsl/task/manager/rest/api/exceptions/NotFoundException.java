package pl.polsl.task.manager.rest.api.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String code) {
        super("Could not found any math for code = " + code);
    }

    public NotFoundException(Long id) {
        super("Could not found any math for id = " + id);
    }

}
