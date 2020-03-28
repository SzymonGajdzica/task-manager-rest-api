package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.CodeAlreadyUsedException;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.Client;
import pl.polsl.task.manager.rest.api.models.Manager;
import pl.polsl.task.manager.rest.api.models.ObjectType;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.repositories.ObjectTypeRepository;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.List;

@Component
public class ObjectTypeServiceImpl implements ObjectTypeService {

    private final ObjectTypeRepository objectTypeRepository;
    private final AuthenticationService authenticationService;
    private final CodeNameService codeNameService;

    public ObjectTypeServiceImpl(ObjectTypeRepository objectTypeRepository, AuthenticationService authenticationService, CodeNameService codeNameService) {
        this.objectTypeRepository = objectTypeRepository;
        this.authenticationService = authenticationService;
        this.codeNameService = codeNameService;
    }

    @Override
    public ObjectType createObjectType(String token, CodeNamePost codeNamePost) {
        codeNameService.validateIfUserCanModify(token);
        if (objectTypeRepository.existsById(codeNamePost.getCode()))
            throw new CodeAlreadyUsedException(codeNamePost.getCode());
        ObjectType objectType = codeNameService.getPatchedCodeName(new ObjectType(), codeNamePost);
        return objectTypeRepository.save(objectType);
    }

    @Override
    public ObjectType getPatchedObjectType(String token, String objectTypeCode, CodeNamePatch codeNamePatch) {
        codeNameService.validateIfUserCanModify(token);
        ObjectType objectType = objectTypeRepository.getById(objectTypeCode);
        return objectTypeRepository.save(codeNameService.getPatchedCodeName(objectType, codeNamePatch));
    }

    @Override
    public List<ObjectType> getObjectsTypes(String token) {
        User currentUser = authenticationService.getUserFromToken(token);
        if (!(currentUser instanceof Manager || currentUser instanceof Client))
            throw new ForbiddenAccessException(Manager.class, Client.class);
        return objectTypeRepository.findAll();
    }

    @Override
    public CodeNameView serialize(ObjectType objectType) {
        return codeNameService.serialize(objectType);
    }

}
