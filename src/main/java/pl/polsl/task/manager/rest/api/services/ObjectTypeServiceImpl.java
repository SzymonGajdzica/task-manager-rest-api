package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.CodeAlreadyUsedException;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.mappers.CodeNameMapper;
import pl.polsl.task.manager.rest.api.models.Client;
import pl.polsl.task.manager.rest.api.models.Manager;
import pl.polsl.task.manager.rest.api.models.ObjectType;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.repositories.ObjectTypeRepository;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectTypeServiceImpl implements ObjectTypeService, StartUpFiller {

    private final ObjectTypeRepository objectTypeRepository;
    private final AuthenticationService authenticationService;
    private final CodeNameMapper codeNameMapper;

    public ObjectTypeServiceImpl(ObjectTypeRepository objectTypeRepository, AuthenticationService authenticationService, CodeNameMapper codeNameMapper) {
        this.objectTypeRepository = objectTypeRepository;
        this.authenticationService = authenticationService;
        this.codeNameMapper = codeNameMapper;
    }

    @Override
    public CodeNameView createObjectType(String token, CodeNamePost codeNamePost) {
        validateIfUserCanModify(token);
        if (objectTypeRepository.existsById(codeNamePost.getCode()))
            throw new CodeAlreadyUsedException(codeNamePost.getCode());
        ObjectType objectType = new ObjectType();
        codeNameMapper.map(codeNamePost, objectType);
        return codeNameMapper.map(objectTypeRepository.save(objectType));
    }

    @Override
    public CodeNameView getPatchedObjectType(String token, String objectTypeCode, CodeNamePatch codeNamePatch) {
        validateIfUserCanModify(token);
        ObjectType objectType = objectTypeRepository.getById(objectTypeCode);
        codeNameMapper.map(codeNamePatch, objectType);
        return codeNameMapper.map(objectTypeRepository.save(objectType));
    }

    @Override
    public List<CodeNameView> getObjectsTypes(String token) {
        User currentUser = authenticationService.getUserFromToken(token);
        if (!(currentUser instanceof Manager || currentUser instanceof Client))
            throw new ForbiddenAccessException(Manager.class, Client.class);
        List<ObjectType> objectTypes = objectTypeRepository.findAll();
        return objectTypes.stream().map(codeNameMapper::map).collect(Collectors.toList());
    }

    private void validateIfUserCanModify(String token) {
        User user = authenticationService.getUserFromToken(token);
        if (!(user instanceof Manager))
            throw new ForbiddenAccessException(Manager.class);
    }

    @Override
    public void createInitialData() throws RuntimeException {
        ObjectType programming = new ObjectType();
        programming.setCode("PRG");
        programming.setName("Programing");
        objectTypeRepository.save(programming);

        ObjectType management = new ObjectType();
        management.setCode("MAN");
        management.setName("Management");
        objectTypeRepository.save(management);
    }

}
