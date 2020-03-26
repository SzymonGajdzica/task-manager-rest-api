package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.CodeAlreadyUsedException;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.*;
import pl.polsl.task.manager.rest.api.repositories.ActivityTypeRepository;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.List;

@Component
public class ActivityTypeServiceImpl implements ActivityTypeService {

    private final ActivityTypeRepository activityTypeRepository;
    private final AuthenticationService authenticationService;
    private final CodeNameService codeNameService;

    public ActivityTypeServiceImpl(ActivityTypeRepository activityTypeRepository, AuthenticationService authenticationService, CodeNameService codeNameService) {
        this.activityTypeRepository = activityTypeRepository;
        this.authenticationService = authenticationService;
        this.codeNameService = codeNameService;
    }

    @Override
    public ActivityType createActivityType(String token, CodeNamePost codeNamePost) {
        codeNameService.validateIfUserCanModify(token);
        if (activityTypeRepository.existsById(codeNamePost.getCode()))
            throw new CodeAlreadyUsedException(codeNamePost.getCode());
        ActivityType activityType = codeNameService.getPatchedCodeName(new ActivityType(), codeNamePost);
        return activityTypeRepository.save(activityType);
    }

    @Override
    public ActivityType getPatchedActivityType(String token, String activityTypeCode, CodeNamePatch codeNamePatch) {
        codeNameService.validateIfUserCanModify(token);
        ActivityType activityType = activityTypeRepository.getById(activityTypeCode);
        return activityTypeRepository.save(codeNameService.getPatchedCodeName(activityType, codeNamePatch));
    }

    @Override
    public List<ActivityType> getActivitiesTypes(String token) {
        User currentUser = authenticationService.getUserFromToken(token);
        if (!(currentUser instanceof Admin || currentUser instanceof Manager || currentUser instanceof Worker))
            throw new ForbiddenAccessException(Manager.class, Admin.class, Worker.class);
        return activityTypeRepository.findAll();
    }

    @Override
    public CodeNameView serialize(ActivityType activityType) {
        return codeNameService.serialize(activityType);
    }

}
