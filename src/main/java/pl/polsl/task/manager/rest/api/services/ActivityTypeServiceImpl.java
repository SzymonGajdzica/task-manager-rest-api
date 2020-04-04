package pl.polsl.task.manager.rest.api.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.CodeAlreadyUsedException;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.ActivityType;
import pl.polsl.task.manager.rest.api.models.Manager;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.models.Worker;
import pl.polsl.task.manager.rest.api.repositories.ActivityTypeRepository;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivityTypeServiceImpl implements ActivityTypeService {

    private final ActivityTypeRepository activityTypeRepository;
    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper;

    public ActivityTypeServiceImpl(ActivityTypeRepository activityTypeRepository, AuthenticationService authenticationService, ModelMapper modelMapper) {
        this.activityTypeRepository = activityTypeRepository;
        this.authenticationService = authenticationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CodeNameView createActivityType(String token, CodeNamePost codeNamePost) {
        validateIfUserCanModify(token);
        if (activityTypeRepository.existsById(codeNamePost.getCode()))
            throw new CodeAlreadyUsedException(codeNamePost.getCode());
        ActivityType activityType = modelMapper.map(codeNamePost, ActivityType.class);
        return modelMapper.map(activityTypeRepository.save(activityType), CodeNameView.class);
    }

    @Override
    public CodeNameView getPatchedActivityType(String token, String activityTypeCode, CodeNamePatch codeNamePatch) {
        validateIfUserCanModify(token);
        ActivityType activityType = activityTypeRepository.getById(activityTypeCode);
        modelMapper.map(codeNamePatch, activityType);
        return modelMapper.map(activityTypeRepository.save(activityType), CodeNameView.class);
    }

    @Override
    public List<CodeNameView> getActivitiesTypes(String token) {
        User currentUser = authenticationService.getUserFromToken(token);
        if (!(currentUser instanceof Manager || currentUser instanceof Worker))
            throw new ForbiddenAccessException(Manager.class, Worker.class);
        List<ActivityType> activityTypes = activityTypeRepository.findAll();
        return activityTypes.stream().map(activityType -> modelMapper.map(activityType, CodeNameView.class)).collect(Collectors.toList());
    }

    private void validateIfUserCanModify(String token) {
        User user = authenticationService.getUserFromToken(token);
        if (!(user instanceof Manager))
            throw new ForbiddenAccessException(Manager.class);
    }

}
