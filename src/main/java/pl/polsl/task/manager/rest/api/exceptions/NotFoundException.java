package pl.polsl.task.manager.rest.api.exceptions;

import pl.polsl.task.manager.rest.api.models.BaseEntityWithCode;
import pl.polsl.task.manager.rest.api.models.BaseEntityWithId;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<? extends BaseEntityWithCode> resourceClass, String code) {
        super("Could not found " + resourceClass.getSimpleName() + " with code " + code);
    }

    public NotFoundException(Class<? extends BaseEntityWithId> resourceClass, Long id) {
        super("Could not found " + resourceClass.getSimpleName() + " with id " + id);
    }

}
