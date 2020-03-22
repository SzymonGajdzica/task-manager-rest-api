package pl.polsl.task.manager.rest.api.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
        super("Could not find item with id = " + id);
    }
    public NotFoundException(String code) {
        super("Could not find item with code = " + code);
    }

}
