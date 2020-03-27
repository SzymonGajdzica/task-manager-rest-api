package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.BadRequestException;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.Object;
import pl.polsl.task.manager.rest.api.models.*;
import pl.polsl.task.manager.rest.api.repositories.ObjectRepository;
import pl.polsl.task.manager.rest.api.repositories.ObjectTypeRepository;
import pl.polsl.task.manager.rest.api.repositories.UserRepository;
import pl.polsl.task.manager.rest.api.views.ObjectPatch;
import pl.polsl.task.manager.rest.api.views.ObjectPost;
import pl.polsl.task.manager.rest.api.views.ObjectView;

import java.util.List;

@Component
public class ObjectServiceImpl implements ObjectService {

    private final ObjectRepository objectRepository;
    private final AuthenticationService authenticationService;
    private final ObjectTypeRepository objectTypeRepository;
    private final UserRepository userRepository;

    public ObjectServiceImpl(ObjectRepository objectRepository, AuthenticationService authenticationService, ObjectTypeRepository objectTypeRepository, UserRepository userRepository) {
        this.objectRepository = objectRepository;
        this.authenticationService = authenticationService;
        this.objectTypeRepository = objectTypeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Object createObject(String token, ObjectPost objectPost) {
        checkModifyPermission(token);
        Object object = new Object();
        User client = userRepository.getById(objectPost.getClientId());
        if (!(client instanceof Client))
            throw new BadRequestException("clientId does not point at Client");
        object.setClient((Client) client);
        object.setObjectType(objectTypeRepository.getById(objectPost.getObjectTypeCode()));
        object.setName(objectPost.getName());
        return objectRepository.save(object);
    }

    @Override
    public Object getPatchedObject(String token, Long objectId, ObjectPatch objectPatch) {
        checkModifyPermission(token);
        Object object = objectRepository.getById(objectId);
        if (objectPatch.getName() != null)
            object.setName(objectPatch.getName());
        return objectRepository.save(object);
    }

    @Override
    public List<Object> getObjects(String token) {
        User user = authenticationService.getUserFromToken(token);
        if (user instanceof Client)
            return objectRepository.getAllByClient((Client) user);
        if (user instanceof Manager || user instanceof Admin)
            return objectRepository.findAll();
        throw new ForbiddenAccessException(Manager.class, Admin.class, Client.class);
    }

    @Override
    public void deleteObject(String token, Long objectId) {
        checkModifyPermission(token);
        objectRepository.delete(objectRepository.getById(objectId));
    }

    private void checkModifyPermission(String token) {
        User user = authenticationService.getUserFromToken(token);
        if (!(user instanceof Manager || user instanceof Admin))
            throw new ForbiddenAccessException(Admin.class, Manager.class);
    }

    @Override
    public ObjectView serialize(Object object) {
        ObjectView objectView = new ObjectView();
        objectView.setId(object.getId());
        objectView.setClientId(object.getClient().getId());
        objectView.setName(object.getName());
        objectView.setObjectTypeCode(object.getObjectType().getCode());
        return objectView;
    }

}
