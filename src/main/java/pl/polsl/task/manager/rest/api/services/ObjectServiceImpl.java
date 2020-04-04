package pl.polsl.task.manager.rest.api.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.BadRequestException;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.Client;
import pl.polsl.task.manager.rest.api.models.Manager;
import pl.polsl.task.manager.rest.api.models.Object;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.repositories.ObjectRepository;
import pl.polsl.task.manager.rest.api.repositories.ObjectTypeRepository;
import pl.polsl.task.manager.rest.api.repositories.UserRepository;
import pl.polsl.task.manager.rest.api.views.ObjectPatch;
import pl.polsl.task.manager.rest.api.views.ObjectPost;
import pl.polsl.task.manager.rest.api.views.ObjectView;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectServiceImpl implements ObjectService {

    private final ObjectRepository objectRepository;
    private final AuthenticationService authenticationService;
    private final ObjectTypeRepository objectTypeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ObjectServiceImpl(ObjectRepository objectRepository, AuthenticationService authenticationService, ObjectTypeRepository objectTypeRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.objectRepository = objectRepository;
        this.authenticationService = authenticationService;
        this.objectTypeRepository = objectTypeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ObjectView createObject(String token, ObjectPost objectPost) {
        checkModifyPermission(token);
        Object object = modelMapper.map(objectPost, Object.class);
        User client = userRepository.getById(objectPost.getClientId());
        if (!(client instanceof Client))
            throw new BadRequestException("clientId does not point at Client");
        object.setClient((Client) client);
        object.setObjectType(objectTypeRepository.getById(objectPost.getObjectTypeCode()));
        return modelMapper.map(objectRepository.save(object), ObjectView.class);
    }

    @Override
    public ObjectView getPatchedObject(String token, Long objectId, ObjectPatch objectPatch) {
        checkModifyPermission(token);
        Object object = objectRepository.getById(objectId);
        modelMapper.map(objectPatch, object);
        return modelMapper.map(objectRepository.save(object), ObjectView.class);
    }

    @Override
    public List<ObjectView> getObjects(String token) {
        User user = authenticationService.getUserFromToken(token);
        List<Object> objects = null;
        if (user instanceof Client)
            objects = ((Client) user).getObjects();
        if (user instanceof Manager)
            objects = objectRepository.findAll();
        if (objects == null)
            throw new ForbiddenAccessException(Manager.class, Client.class);
        return objects.stream().map(object -> modelMapper.map(object, ObjectView.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteObject(String token, Long objectId) {
        checkModifyPermission(token);
        objectRepository.delete(objectRepository.getById(objectId));
    }

    private void checkModifyPermission(String token) {
        User user = authenticationService.getUserFromToken(token);
        if (!(user instanceof Manager))
            throw new ForbiddenAccessException(Manager.class);
    }

}
